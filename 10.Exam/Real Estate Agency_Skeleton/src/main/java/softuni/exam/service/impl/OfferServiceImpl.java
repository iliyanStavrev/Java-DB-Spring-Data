package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferSeedRootDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION" +
            "\\EXAM\\Real Estate Agency_Skeleton\\skeleton\\src\\main\\" +
            "resources\\files\\xml\\offers.xml";

    private final OfferRepository offerRepository;
    private final ApartmentRepository apartmentRepository;
    private final AgentRepository agentRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public OfferServiceImpl(OfferRepository offerRepository, ApartmentRepository apartmentRepository,
                            AgentRepository agentRepository, ModelMapper modelMapper,
                            XmlParser xmlParser, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.apartmentRepository = apartmentRepository;
        this.agentRepository = agentRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        OfferSeedRootDto offerSeedRootDto = xmlParser
                .fromFile(PATH, OfferSeedRootDto.class);

        offerSeedRootDto.getOffer()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);

                    Agent agent = agentRepository
                            .findByFirstName(offerSeedDto.getAgent().getName());

                    if (agent == null) {
                        stringBuilder.append("Invalid offer")
                                .append(System.lineSeparator());
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported offer %.2f",
                                    offerSeedDto.getPrice())
                                    : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(offerSeedDto -> {
                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    offer.setAgent(agentRepository
                            .findByFirstName(offerSeedDto.getAgent().getName()));
                    offer.setApartment(apartmentRepository
                            .findById(offerSeedDto.getApartment().getId()).orElse(null));

                    return offer;
                }).forEach(offerRepository::save);

        return stringBuilder.toString();
    }

    @Override
    public String exportOffers() {

        return offerRepository
                .findBestOffers()
                .stream()
                .map(offer -> String.format("Agent %s %s with offer №%d:\n" +
                        "\t-Apartment area: %.2f\n" +
                        "\t--Town: %s\n" +
                        "\t---Price: %.2f$\n",
                        offer.getAgent().getFirstName(),
                        offer.getAgent().getLastName(),
                        offer.getId(),
                        offer.getApartment().getArea(),
                        offer.getApartment().getTown().getTownName(),
                        offer.getPrice()))
                .collect(Collectors.joining(""));
    }
}
