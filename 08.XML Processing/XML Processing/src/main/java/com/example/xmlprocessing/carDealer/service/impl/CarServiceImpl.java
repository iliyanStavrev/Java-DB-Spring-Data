package com.example.xmlprocessing.carDealer.service.impl;


import com.example.xmlprocessing.carDealer.model.dto.query2.CarFromMakeDto;
import com.example.xmlprocessing.carDealer.model.dto.query2.CarFromMakeRootDto;
import com.example.xmlprocessing.carDealer.model.dto.query4.CarsAndPartsRootDto;
import com.example.xmlprocessing.carDealer.model.dto.query4.CarsDto;
import com.example.xmlprocessing.carDealer.model.dto.query4.PartsDto;
import com.example.xmlprocessing.carDealer.model.dto.query4.PartsRootDto;
import com.example.xmlprocessing.carDealer.model.dto.seed.CarSeedRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Car;
import com.example.xmlprocessing.carDealer.repository.CarRepository;
import com.example.xmlprocessing.carDealer.service.CarService;

import com.example.xmlprocessing.carDealer.service.PartService;
import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private static final String PATH = "src/main/resources/file/data/cars.xml";

    private final ValidationUtil validationUtil;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final PartService partService;
    private final XmlParser xmlParser;


    public CarServiceImpl(ValidationUtil validationUtil,
                          CarRepository carRepository, ModelMapper modelMapper,
                          PartService partService, XmlParser xmlParser) {
        this.validationUtil = validationUtil;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.partService = partService;

        this.xmlParser = xmlParser;
    }

    @Override
    public void seed() throws JAXBException, FileNotFoundException {

        if (carRepository.count() > 0){
            return;
        }

        CarSeedRootDto carSeedRootDto = xmlParser
                .fromFile(PATH, CarSeedRootDto.class);

        carSeedRootDto.getCar()
                .stream()
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
        return carRepository.findById(ThreadLocalRandom
                .current().nextLong(1, carRepository.count() + 1))
                .orElse(null);
    }

    @Override
    public CarFromMakeRootDto getCarsFromMake(String make) {

        List<CarFromMakeDto> carFromMakeDtos = carRepository
                .findAllByMakeOrderByModelTravelledDistanceDesc(make)
                .stream()
                .map(car -> modelMapper.map(car, CarFromMakeDto.class))
                .toList();
        CarFromMakeRootDto car = new CarFromMakeRootDto();
        car.setCar(carFromMakeDtos);

        return car;
    }

    @Override
    public CarsAndPartsRootDto getAllCarsAndParts() {

        List<CarsDto> cars = carRepository.getAllCarsAndParts()
                .stream()
                .map(car -> {
                    CarsDto carsDto = modelMapper.map(car, CarsDto.class);
                    PartsRootDto partsRootDto = new PartsRootDto();
                    partsRootDto.setPart(car.getParts()
                            .stream()
                            .map(part -> modelMapper.map(part, PartsDto.class))
                            .collect(Collectors.toList()));
                    carsDto.setParts(partsRootDto);
                    return carsDto;
                }).toList();

        CarsAndPartsRootDto carsAndPartsRootDto = new CarsAndPartsRootDto();
        carsAndPartsRootDto.setCar(cars);

        return carsAndPartsRootDto;
    }
}
