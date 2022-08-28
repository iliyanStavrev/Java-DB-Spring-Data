package com.example.xmlprocessing.carDealer.model.dto.query5;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersSalesRootDto {

    private List<CustomersSalesDto> customer;

    public List<CustomersSalesDto> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomersSalesDto> customer) {
        this.customer = customer;
    }
}
