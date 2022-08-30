package hiberspring.service.impl;

import com.google.gson.Gson;
import hiberspring.model.dtos.CardSeedDto;
import hiberspring.model.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.service.EmployeeCardService;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private static final String PATH = "D:\\Работен плот\\JAVA DB SPRING\\EXAM_PREPARATION" +
            "\\20 Dec 2018\\Hiberspring Inc._Skeleton\\HiberSpring\\skeleton\\src\\" +
            "main\\resources\\files\\employee-cards.json";

    private final EmployeeCardRepository employeeCardRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, ModelMapper modelMapper,
                                   Gson gson, ValidationUtil validationUtil) {
        this.employeeCardRepository = employeeCardRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return Files.readString(Path.of(PATH));
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();

        CardSeedDto[] cardSeedDtos = gson
                .fromJson(readEmployeeCardsJsonFile(), CardSeedDto[].class);

        Arrays.stream(cardSeedDtos)
                .filter(cardSeedDto -> {
                    boolean isValid = validationUtil.isValid(cardSeedDto);

                    EmployeeCard card = employeeCardRepository
                            .findByNumber(cardSeedDto.getNumber());

                    if (card != null) {
                        stringBuilder.append("Error: Invalid data.")
                                .append(System.lineSeparator());
                        return false;
                    }

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported Employee Card %s.",
                                    cardSeedDto.getNumber())
                                    : "Error: Invalid data.")
                            .append(System.lineSeparator());

                    return isValid;
                }).map(cardSeedDto -> modelMapper
                        .map(cardSeedDto, EmployeeCard.class))
                .forEach(employeeCardRepository::save);

        return stringBuilder.toString();
    }
}
