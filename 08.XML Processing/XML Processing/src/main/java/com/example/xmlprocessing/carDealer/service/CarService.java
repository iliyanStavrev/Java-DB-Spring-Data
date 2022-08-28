package com.example.xmlprocessing.carDealer.service;



import com.example.xmlprocessing.carDealer.model.dto.query2.CarFromMakeRootDto;
import com.example.xmlprocessing.carDealer.model.dto.query4.CarsAndPartsRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Car;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CarService {

    void seed() throws JAXBException, FileNotFoundException;

    Car getRandomCar();

    CarFromMakeRootDto getCarsFromMake(String make);

    CarsAndPartsRootDto getAllCarsAndParts();

}
