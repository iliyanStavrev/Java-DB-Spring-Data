package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PlaneSeedRootDto;
import softuni.exam.models.entity.Plane;
import softuni.exam.repository.PlaneRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.PlaneService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PlaneServiceImpl implements PlaneService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION" +
            "\\03 Apr 2020\\Airline_Skeleton\\Airlane\\sleleton\\src\\" +
            "main\\resources\\files\\xml\\planes.xml";

    private final PlaneRepository planeRepository;
    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;

    public PlaneServiceImpl(PlaneRepository planeRepository,
                            TownRepository townRepository, ModelMapper modelMapper,
                            XmlParser xmlParser, ValidationUtil validationUtil) {
        this.planeRepository = planeRepository;
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return planeRepository.count() > 0;
    }

    @Override
    public String readPlanesFileContent() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importPlanes() throws JAXBException, FileNotFoundException {

        StringBuilder stringBuilder = new StringBuilder();

        PlaneSeedRootDto planeSeedRootDto = xmlParser
                .fromFile(PATH, PlaneSeedRootDto.class);

        planeSeedRootDto.getPlane()
                .stream()
                .filter(planeSeedDto -> {
                    boolean isValid = validationUtil.isValid(planeSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Plane %s",
                                    planeSeedDto.getRegisterNumber())
                                    : "Invalid Plane")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(planeSeedDto -> modelMapper
                        .map(planeSeedDto, Plane.class))
                .forEach(planeRepository::save);

        return stringBuilder.toString();
    }
}
