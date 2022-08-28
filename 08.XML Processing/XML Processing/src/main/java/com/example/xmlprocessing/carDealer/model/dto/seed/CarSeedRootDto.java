package com.example.xmlprocessing.carDealer.model.dto.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarSeedRootDto {

    private List<CarSeedDto> car;

    public List<CarSeedDto> getCar() {
        return car;
    }

    public void setCar(List<CarSeedDto> car) {
        this.car = car;
    }
}
