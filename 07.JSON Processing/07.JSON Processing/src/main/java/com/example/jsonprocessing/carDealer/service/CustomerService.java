package com.example.jsonprocessing.carDealer.service;

import com.example.jsonprocessing.carDealer.model.dto.CustomerByBirthDateDto;
import com.example.jsonprocessing.carDealer.model.entity.Customer;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    void seed() throws IOException;

    Customer getRandomCustomer();

    List<CustomerByBirthDateDto> findAllByBirthDate();
}
