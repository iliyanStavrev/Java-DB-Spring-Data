package com.example.jsonprocessing.carDealer.service.impl;

import com.example.jsonprocessing.carDealer.model.dto.*;
import com.example.jsonprocessing.carDealer.model.entity.Car;
import com.example.jsonprocessing.carDealer.repository.CarRepository;
import com.example.jsonprocessing.carDealer.service.CarService;
import com.example.jsonprocessing.carDealer.service.PartService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String PATH = "src/main/resources/files/data/cars.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;


    public CarServiceImpl(Gson gson, ValidationUtil validationUtil,
                          CarRepository carRepository, ModelMapper modelMapper,
                          PartService partService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;
       ;
    }

    @Override
    public void seed() throws IOException {
        if (carRepository.count() > 0){
            return;
        }

        CarSeedDto[] carSeedDtos = gson.fromJson(Files
                .readString(Path.of(PATH)),CarSeedDto[].class);

        Arrays.stream(carSeedDtos)
                .filter(validationUtil::isValid)
                .map(carSeedDto -> {
                    Car car = modelMapper.map(carSeedDto, Car.class);
                    car.setParts(partService.getRandomListOfParts());
                    return car;
                })
                .forEach(carRepository::save);


    }

    @Override
    public Car getRandomCar() {

        Long id = ThreadLocalRandom.current()
                .nextLong(1, carRepository.count() + 1);

        return carRepository.findById(id).orElse(null);
    }

    @Override
    public List<CarFromMakeDto> findAllByMake(String make) {

        return carRepository
                .findAllByMakeOrderByModelTravelledDistanceDesc(make)
                .stream()
                .map(c -> modelMapper.map(c, CarFromMakeDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<CarsAndPartsDto> getAllCarsAndParts() {

        return carRepository.getAllCarsAndParts()
                .stream()
                .map(car -> {
                    CarsAndPartsDto carsAndPartsDto = new CarsAndPartsDto();
                    carsAndPartsDto.setCar(modelMapper
                            .map(car, CarsMakeModelDistanceDto.class));
                    carsAndPartsDto.setParts(modelMapper
                            .map(car.getParts(), PartsNamePriceDto[].class));
                    return carsAndPartsDto;
                })
                .collect(Collectors.toList());


    }
}
