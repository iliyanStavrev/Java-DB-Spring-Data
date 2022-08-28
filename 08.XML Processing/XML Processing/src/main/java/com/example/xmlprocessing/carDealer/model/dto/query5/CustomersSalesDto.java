package com.example.xmlprocessing.carDealer.model.dto.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersSalesDto {

    @XmlAttribute(name = "full-name")
    private String name;
    @XmlAttribute(name = "bought-cars")
    private Integer boughtCars;
    @XmlAttribute(name = "spent-money")
    private Double spentMoney;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public Double getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(Double spentMoney) {
        this.spentMoney = spentMoney;
    }
}
