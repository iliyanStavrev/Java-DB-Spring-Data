package com.example.xmlprocessing.carDealer.model.dto.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarFromMakeRootDto {

    private List<CarFromMakeDto> car;

    public List<CarFromMakeDto> getCar() {
        return car;
    }

    public void setCar(List<CarFromMakeDto> car) {
        this.car = car;
    }
}
