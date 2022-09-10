package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferSeedRootDto;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.OfferService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String FILE_PATH = "src/main/resources/files/xml/offers.xml";


    private final OfferRepository offerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public OfferServiceImpl(OfferRepository offerRepository, XmlParser xmlParser,
                            ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.offerRepository = offerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files
                .readString(Path.of(FILE_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        OfferSeedRootDto offerSeedRootDto =
                xmlParser.fromFile(FILE_PATH, OfferSeedRootDto.class);

        offerSeedRootDto.getOffer()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully import offer %s - %s",
                                    offerSeedDto.getAddedOn(), offerSeedDto.getHasGoldStatus())
                                    : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(offerSeedDto -> modelMapper.map(offerSeedDto, Offer.class))
                .forEach(offerRepository::save);
        return stringBuilder.toString();
    }
}
