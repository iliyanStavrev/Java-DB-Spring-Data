package com.example.jsonprocessing.carDealer.service.impl;

import com.example.jsonprocessing.carDealer.model.dto.CustomerByBirthDateDto;
import com.example.jsonprocessing.carDealer.model.dto.CustomerSeedDto;
import com.example.jsonprocessing.carDealer.model.entity.Customer;
import com.example.jsonprocessing.carDealer.model.entity.Sale;
import com.example.jsonprocessing.carDealer.repository.CustomerRepository;
import com.example.jsonprocessing.carDealer.repository.SaleRepository;
import com.example.jsonprocessing.carDealer.service.CustomerService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String PATH = "src/main/resources/files/data/customers.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final SaleRepository saleRepository;

    public CustomerServiceImpl(Gson gson, ValidationUtil validationUtil,
                               CustomerRepository customerRepository,
                               ModelMapper modelMapper, SaleRepository saleRepository) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.saleRepository = saleRepository;
    }

    @Override
    public void seed() throws IOException {
        if (customerRepository.count() > 0){
            return;
        }

        CustomerSeedDto[] customerSeedDtos = gson.fromJson(
                Files.readString(Path.of(PATH)),CustomerSeedDto[].class);

        Arrays.stream(customerSeedDtos)
                .filter(validationUtil::isValid)
                .map(c -> modelMapper.map(c, Customer.class))
                .forEach(c -> customerRepository.save(c));
    }

    @Override
    public Customer getRandomCustomer() {
        Long id = ThreadLocalRandom.current()
                .nextLong(1, customerRepository.count() + 1);

        return customerRepository.findById(id).orElse(null);
    }

    @Override
    public List<CustomerByBirthDateDto> findAllByBirthDate() {

        return customerRepository.getAllOrderByBirthDate()
                .stream()
                .map(customer ->
                   modelMapper.map(customer, CustomerByBirthDateDto.class)
                )
                .sorted(Comparator.comparing(CustomerByBirthDateDto::getBirthDate)
                        .thenComparing(CustomerByBirthDateDto::getYoungDriver))
                .collect(Collectors.toList());
    }
}
