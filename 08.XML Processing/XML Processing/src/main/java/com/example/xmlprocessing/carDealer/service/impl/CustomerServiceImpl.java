package com.example.xmlprocessing.carDealer.service.impl;


import com.example.xmlprocessing.carDealer.model.dto.query1.CustomerOrderedDto;
import com.example.xmlprocessing.carDealer.model.dto.query1.CustomersOrderedRootDto;
import com.example.xmlprocessing.carDealer.model.dto.seed.CustomerSeedRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Customer;
import com.example.xmlprocessing.carDealer.repository.CustomerRepository;
import com.example.xmlprocessing.carDealer.repository.SaleRepository;
import com.example.xmlprocessing.carDealer.service.CustomerService;
import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String PATH = "src/main/resources/file/data/customers.xml";


    private final ValidationUtil validationUtil;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final SaleRepository saleRepository;
    private final XmlParser xmlParser;

    public CustomerServiceImpl(ValidationUtil validationUtil,
                               CustomerRepository customerRepository,
                               ModelMapper modelMapper, SaleRepository saleRepository, XmlParser xmlParser) {
        this.validationUtil = validationUtil;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.saleRepository = saleRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seed() throws JAXBException, FileNotFoundException {

        if (customerRepository.count() > 0){
            return;
        }

        CustomerSeedRootDto customerSeedRootDto =
                xmlParser.fromFile(PATH, CustomerSeedRootDto.class);

        customerSeedRootDto.getCustomer()
                .stream()
                .filter(validationUtil::isValid)
                .map(customerSeedDto -> modelMapper
                        .map(customerSeedDto, Customer.class))
                .forEach(customerRepository::save);
    }

    @Override
    public Customer getRandomCustomer() {

        return customerRepository.findById(ThreadLocalRandom
                .current().nextLong(1, customerRepository.count() + 1))
                .orElse(null);
    }

    @Override
    public CustomersOrderedRootDto getAllCustomersOrdered() {

        List<CustomerOrderedDto> customers = customerRepository
                .getAllOrderByBirthDate()
                .stream()
                .map(customer -> modelMapper
                        .map(customer, CustomerOrderedDto.class))
                .sorted(Comparator.comparing(CustomerOrderedDto::getBirthDate)
                        .thenComparing(CustomerOrderedDto::getYoungDriver))
                .toList();

        CustomersOrderedRootDto customersOrderedRootDto =
                new CustomersOrderedRootDto();

        customersOrderedRootDto.setCustomer(customers);

        return customersOrderedRootDto;
    }
}
