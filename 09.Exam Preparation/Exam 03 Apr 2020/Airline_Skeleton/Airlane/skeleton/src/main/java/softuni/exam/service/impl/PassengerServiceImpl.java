package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PassengerSeedDto;
import softuni.exam.models.entity.Passenger;
import softuni.exam.repository.PassengerRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PassengerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
public class PassengerServiceImpl implements PassengerService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION\\" +
            "03 Apr 2020\\Airline_Skeleton\\Airlane\\sleleton\\src\\" +
            "main\\resources\\files\\json\\passengers.json";

    private final PassengerRepository passengerRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public PassengerServiceImpl(PassengerRepository passengerRepository, TownRepository townRepository, ModelMapper modelMapper,
                                Gson gson, ValidationUtil validationUtil) {
        this.passengerRepository = passengerRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return passengerRepository.count() > 0;
    }

    @Override
    public String readPassengersFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPassengers() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        PassengerSeedDto[] passengerSeedDtos = gson
                .fromJson(readPassengersFileContent(), PassengerSeedDto[].class);

        Arrays.stream(passengerSeedDtos)
                .filter(passengerSeedDto -> {
                    boolean isValid = validationUtil.isValid(passengerSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Passenger %s - %s",
                                    passengerSeedDto.getLastName(),
                                    passengerSeedDto.getEmail())
                                    : "Invalid Passenger")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(passengerSeedDto -> {
                    Passenger passenger = modelMapper
                            .map(passengerSeedDto,Passenger.class);

                    passenger.setTown(townRepository
                            .findByName(passengerSeedDto.getTown()));

                    return passenger;
                }).forEach(passengerRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String getPassengersOrderByTicketsCountDescendingThenByEmail() {
        return passengerRepository
                .getAllPassengers()
                .stream()
                .map(passenger -> String.format("Passenger %s %s\n" +
                        "\tEmail - %s\n" +
                        "\tPhone - %s\n" +
                        "\tNumber of tickets - %d\n",
                        passenger.getFirstName(),
                        passenger.getLastName(),
                        passenger.getEmail(),
                        passenger.getPhoneNumber(),
                        passenger.getTickets().size()))
                .collect(Collectors.joining(""));
    }
}
