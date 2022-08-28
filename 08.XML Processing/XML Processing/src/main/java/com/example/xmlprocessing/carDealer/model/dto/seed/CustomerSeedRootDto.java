package com.example.xmlprocessing.carDealer.model.dto.seed;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerSeedRootDto {

    private List<CustomerSeedDto> customer;

    public List<CustomerSeedDto> getCustomer() {
        return customer;
    }

    public void setCustomer(List<CustomerSeedDto> customer) {
        this.customer = customer;
    }
}
