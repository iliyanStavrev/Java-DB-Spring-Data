package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ApartmentSeedRootDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\EXAM\\Real Estate Agency_Skeleton\\skeleton\\src\\main\\" +
            "resources\\files\\xml\\apartments.xml";

    private final ApartmentRepository apartmentRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository,
                                ModelMapper modelMapper, XmlParser xmlParser, ValidationUtil validationUtil) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        ApartmentSeedRootDto apartmentSeedRootDto = xmlParser
                .fromFile(PATH, ApartmentSeedRootDto.class);

        apartmentSeedRootDto.getApartment()
                .stream()
                .filter(apartmentSeedDto -> {
                    boolean isValid = validationUtil.isValid(apartmentSeedDto);

                    Apartment apartment = apartmentRepository
                            .findByTown_TownNameAndArea(
                                    apartmentSeedDto.getTown(),
                                    apartmentSeedDto.getArea());

                    if (apartment != null) {
                        stringBuilder.append("Invalid apartment")
                                .append(System.lineSeparator());
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported apartment %s - %.2f",
                                    apartmentSeedDto.getApartmentType(),
                                    apartmentSeedDto.getArea())
                                    : "Invalid apartment")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(apartmentSeedDto -> {
                    Apartment apartment = modelMapper
                            .map(apartmentSeedDto, Apartment.class);
                    apartment.setTown(townRepository
                            .findByTownName(apartmentSeedDto.getTown()));
                    return apartment;
                }).forEach(apartmentRepository::save);

        return stringBuilder.toString();
    }
}
