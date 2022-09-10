package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellersSeedRootDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String FILE_PATH = "src/main/resources/files/xml/sellers.xml";

    private final SellerRepository sellerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository,
                             XmlParser xmlParser, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files
                .readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        StringBuilder stringBuilder = new StringBuilder();

        SellersSeedRootDto sellersSeedRootDto =
                xmlParser.fromFile(FILE_PATH, SellersSeedRootDto.class);

        sellersSeedRootDto.getSellers()
                .stream()
                .filter(sellerSeedDto -> {
                    boolean isValid = validationUtil.isValid(sellerSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully import seller %s - %s",
                                    sellerSeedDto.getFirstName(),sellerSeedDto.getEmail())
                                    : "Invalid seller")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(sellerSeedDto -> modelMapper
                        .map(sellerSeedDto, Seller.class))
                .forEach(sellerRepository::save);

        return stringBuilder.toString();
    }
}
