package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarExportDto;
import softuni.exam.models.dto.CarSeedDto;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String FILE_PATH = "src/main/resources/files/json/cars.json";

    private final CarRepository carRepository;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {

        return Files
                .readString(Path.of(FILE_PATH));
    }

    @Override
    public String importCars() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        CarSeedDto[] carSeedDtos
                = gson.fromJson(readCarsFileContent(), CarSeedDto[].class);

        Arrays.stream(gson
                .fromJson(readCarsFileContent(),CarSeedDto[].class))
                .filter(carSeedDto -> {
                    boolean isValid = validationUtil.isValid(carSeedDto);

                    stringBuilder.append(isValid
                            ? String.format("Successfully imported car - %s - %s",
                            carSeedDto.getMake(),carSeedDto.getModel())
                            : "Invalid car")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDto -> modelMapper.map(carSeedDto, Car.class))
                .forEach(carRepository::save);


        return stringBuilder.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        return getAllCars()
                .stream()
                .map(carExportDto -> String.format("Car make - %s, model - %s\n" +
                        "\tKilometers - %d\n" +
                        "\tRegistered on - %s\n" +
                        "\tNumber of pictures - %d\n",carExportDto.getMake(),
                        carExportDto.getModel(),carExportDto.getKilometers(),
                        carExportDto.getRegisteredOn(),carExportDto.getNumberOfPictures()))
                .collect(Collectors.joining(""));
    }

    @Override
    public List<CarExportDto> getAllCars() {

        return carRepository.getAllCars()
                .stream()
                .map(car -> {
                    CarExportDto carExportDto =
                            modelMapper.map(car, CarExportDto.class);
                    carExportDto.setNumberOfPictures(car.getPictures().size());
                    return carExportDto;
                })
                .collect(Collectors.toList());

    }
}
