package com.example.jsonprocessing.carDealer.model.dto;

import com.google.gson.annotations.Expose;

public class CarsAndPartsDto {

    @Expose
    private CarsMakeModelDistanceDto car;
    @Expose
    private PartsNamePriceDto[] parts;

    public CarsMakeModelDistanceDto getCar() {
        return car;
    }

    public void setCar(CarsMakeModelDistanceDto car) {
        this.car = car;
    }

    public PartsNamePriceDto[] getParts() {
        return parts;
    }

    public void setParts(PartsNamePriceDto[] parts) {
        this.parts = parts;
    }
}
