package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.model.dtos.TownSeedDto;
import hiberspring.model.entities.Town;
import hiberspring.repository.TownRepository;
import hiberspring.service.TownService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class TownServiceImpl implements TownService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\" +
            "EXAM_PREPARATION\\20 Dec 2018\\Hiberspring Inc._Skeleton\\HiberSpring\\skeleton\\src\\" +
            "main\\resources\\files\\towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper,
                           Gson gson, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importTowns(String townsFileContent) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        TownSeedDto[] townSeedDtos = gson
                .fromJson(readTownsJsonFile(), TownSeedDto[].class);

        Arrays.stream(townSeedDtos)
                .filter(townSeedDto -> {
                    boolean isValid = validationUtil.isValid(townSeedDto);

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Town %s.",
                                    townSeedDto.getName())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(townSeedDto -> modelMapper
                        .map(townSeedDto, Town.class))
                .forEach(townRepository::save);

        return stringBuilder.toString();
    }
}
