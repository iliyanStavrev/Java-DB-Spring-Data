package com.example.xmlprocessing.carDealer.model.dto.query4;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarsAndPartsRootDto {

    private List<CarsDto> car;

    public List<CarsDto> getCar() {
        return car;
    }

    public void setCar(List<CarsDto> car) {
        this.car = car;
    }
}
