package com.example.xmlprocessing.carDealer.model.dto.query6;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sales")
@XmlAccessorType(XmlAccessType.FIELD)
public class SalesRootDto {

    private List<SalesDto> sale;

    public List<SalesDto> getSale() {
        return sale;
    }

    public void setSale(List<SalesDto> sale) {
        this.sale = sale;
    }
}
