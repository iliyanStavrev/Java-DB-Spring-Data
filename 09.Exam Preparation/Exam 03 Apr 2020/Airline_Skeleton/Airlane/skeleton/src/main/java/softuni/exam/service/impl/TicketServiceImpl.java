package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TicketSeedRootDto;
import softuni.exam.models.entity.Ticket;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TicketRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TicketService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class TicketServiceImpl implements TicketService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION" +
            "\\03 Apr 2020\\Airline_Skeleton\\Airlane\\sleleton\\" +
            "src\\main\\resources\\files\\xml\\tickets.xml";

    private final TicketRepository ticketRepository;
    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;
    private final PassengerRepository passengerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public TicketServiceImpl(TicketRepository ticketRepository, PlaneRepository planeRepository,
                             TownRepository townRepository,
                             PassengerRepository passengerRepository, ModelMapper modelMapper,
                             XmlParser xmlParser, ValidationUtil validationUtil) {
        this.ticketRepository = ticketRepository;
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.passengerRepository = passengerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return ticketRepository.count() > 0;
    }

    @Override
    public String readTicketsFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importTickets() throws JAXBException, FileNotFoundException {
        StringBuilder stringBuilder = new StringBuilder();

        TicketSeedRootDto ticketSeedRootDto = xmlParser
                .fromFile(PATH, TicketSeedRootDto.class);

        ticketSeedRootDto.getTicket()
                .stream()
                .filter(ticketSeedDto -> {
                    boolean isValid = validationUtil.isValid(ticketSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Ticket %s - %s",
                                    ticketSeedDto.getFromTown().getName(),
                                    ticketSeedDto.getToTown().getName())
                                    : "Invalid Ticket")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(ticketSeedDto -> {
                    Ticket ticket = modelMapper
                            .map(ticketSeedDto, Ticket.class);
                    ticket.setFrom(townRepository
                            .findByName(ticketSeedDto.getFromTown().getName()));
                    ticket.setTo(townRepository
                            .findByName(ticketSeedDto.getToTown().getName()));
                    ticket.setPassenger(passengerRepository
                            .findByEmail(ticketSeedDto.getPassenger().getEmail()));
                    ticket.setPlane(planeRepository
                            .findByRegisterNumber(ticketSeedDto.getPlane().getRegisterNumber()));

                    return ticket;
                }).forEach(ticketRepository::save);

        return stringBuilder.toString();
    }
}
