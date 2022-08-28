package com.example.xmlprocessing.carDealer.service.impl;


import com.example.xmlprocessing.carDealer.model.dto.query4.CarsDto;
import com.example.xmlprocessing.carDealer.model.dto.query5.CustomersSalesDto;
import com.example.xmlprocessing.carDealer.model.dto.query5.CustomersSalesRootDto;
import com.example.xmlprocessing.carDealer.model.dto.query6.SalesDto;
import com.example.xmlprocessing.carDealer.model.dto.query6.SalesRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Sale;
import com.example.xmlprocessing.carDealer.repository.SaleRepository;
import com.example.xmlprocessing.carDealer.service.CarService;
import com.example.xmlprocessing.carDealer.service.CustomerService;
import com.example.xmlprocessing.carDealer.service.SaleService;
import com.example.xmlprocessing.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {


    private final ValidationUtil validationUtil;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final CarService carService;

    public SaleServiceImpl(ValidationUtil validationUtil,
                           SaleRepository saleRepository, ModelMapper modelMapper,
                           CustomerService customerService, CarService carService) {

        this.validationUtil = validationUtil;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.carService = carService;
    }

    @Override
    public void seed() {

        for (int i = 0; i < 50; i++) {
            Sale sale = new Sale();

            sale.setCustomer(customerService.getRandomCustomer());
            sale.setCar(carService.getRandomCar());
            switch (sale.getCar().getMake().length()) {
                case 3:
                    sale.setDiscount(0.05);
                    break;
                case 4:
                    sale.setDiscount(0.1);
                    break;
                case 5:
                    sale.setDiscount(0.15);
                    break;
                case 6:
                    sale.setDiscount(0.2);
                    break;
                case 7:
                    sale.setDiscount(0.3);
                    break;
                case 8:
                    sale.setDiscount(0.4);
                    break;
                case 9:
                    sale.setDiscount(0.5);
                    break;
                default:
                    sale.setDiscount(0.0);
            }
            saleRepository.save(sale);
        }
    }

    @Override
    public CustomersSalesRootDto getTotalSalesByCustomer() {

        List<CustomersSalesDto> customers = saleRepository.getTotalSalesByCustomer()
                .stream()
                .map(sale -> {
                    CustomersSalesDto customersSalesDto =
                            modelMapper.map(sale, CustomersSalesDto.class);
                    customersSalesDto.setName(sale.getCustomer().getName());
                    customersSalesDto.setBoughtCars(sale.getCustomer()
                            .getSales().size());
                    Double spentMoney = sale.getCustomer()
                            .getSales()
                            .stream()
                            .mapToDouble(customer -> customer.getCar().getParts()
                                    .stream()
                                    .mapToDouble(part -> part.getPrice().doubleValue()).sum())
                            .sum();
                    customersSalesDto.setSpentMoney(spentMoney);
                    return customersSalesDto;
                })
                .sorted(Comparator.comparing(CustomersSalesDto::getSpentMoney,
                                Comparator.reverseOrder())
                        .thenComparing(CustomersSalesDto::getBoughtCars,
                                Comparator.reverseOrder()))
                .toList();

        CustomersSalesRootDto customersSalesRootDto = new CustomersSalesRootDto();
        customersSalesRootDto.setCustomer(customers);

        return customersSalesRootDto;
    }

    @Override
    public SalesRootDto getSalesWithAppliedDiscount() {

        List<SalesDto> sales = saleRepository.getSalesWithAppliedDiscount()
                .stream()
                .map(sale -> {
                    CarsDto carsDto = modelMapper
                            .map(sale.getCar(), CarsDto.class);
                    SalesDto salesDto = modelMapper
                            .map(sale, SalesDto.class);
                    salesDto.setCar(carsDto);
                    salesDto.setName(sale.getCustomer().getName());

                    BigDecimal price = BigDecimal.valueOf(sale.getCar()
                            .getParts()
                            .stream()
                            .mapToDouble(part -> part.getPrice().doubleValue())
                            .sum());
                    salesDto.setPrice(price);
                    salesDto.setDiscount(sale.getDiscount());
                    salesDto.setPriceWithDiscount(price
                            .multiply(BigDecimal.valueOf(1 - sale.getDiscount())));
                    return salesDto;
                })
                .toList();

        SalesRootDto salesRootDto = new SalesRootDto();
        salesRootDto.setSale(sales);
        return salesRootDto;
    }
}
