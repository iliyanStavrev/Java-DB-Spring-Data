package com.example.xmlprocessing.carDealer.model.dto.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SuppliersRootDto {

    private List<SuppliersDto> supplier;

    public List<SuppliersDto> getSupplier() {
        return supplier;
    }

    public void setSupplier(List<SuppliersDto> supplier) {
        this.supplier = supplier;
    }
}
