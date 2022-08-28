package com.example.xmlprocessing.carDealer.service;


import com.example.xmlprocessing.carDealer.model.dto.query1.CustomersOrderedRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Customer;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface CustomerService {

    void seed() throws JAXBException, FileNotFoundException;

    Customer getRandomCustomer();

    CustomersOrderedRootDto getAllCustomersOrdered();

}
