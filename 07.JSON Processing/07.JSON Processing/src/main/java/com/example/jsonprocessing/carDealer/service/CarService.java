package com.example.jsonprocessing.carDealer.service;

import com.example.jsonprocessing.carDealer.model.dto.CarFromMakeDto;
import com.example.jsonprocessing.carDealer.model.dto.CarsAndPartsDto;
import com.example.jsonprocessing.carDealer.model.entity.Car;

import java.io.IOException;
import java.util.List;

public interface CarService {

    void seed() throws IOException;

    Car getRandomCar();

    List<CarFromMakeDto> findAllByMake(String make);

    List<CarsAndPartsDto> getAllCarsAndParts();
}
