package com.example.xmlprocessing.carDealer.model.dto.query1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomersOrderedRootDto {

    private List<CustomerOrderedDto> customer;

    public List<CustomerOrderedDto> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerOrderedDto> customer) {
        this.customer = customer;
    }
}
