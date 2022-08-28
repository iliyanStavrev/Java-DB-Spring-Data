package com.example.jsonprocessing.carDealer.service.impl;

import com.example.jsonprocessing.carDealer.model.dto.SalesByCustomerDto;
import com.example.jsonprocessing.carDealer.model.dto.SalesSeedDto;
import com.example.jsonprocessing.carDealer.model.dto.SalesWithAppliedDiscountDto;
import com.example.jsonprocessing.carDealer.model.entity.Car;
import com.example.jsonprocessing.carDealer.model.entity.Customer;
import com.example.jsonprocessing.carDealer.model.entity.Sale;
import com.example.jsonprocessing.carDealer.repository.SaleRepository;
import com.example.jsonprocessing.carDealer.service.CarService;
import com.example.jsonprocessing.carDealer.service.CustomerService;
import com.example.jsonprocessing.carDealer.service.SaleService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final CarService carService;

    public SaleServiceImpl(Gson gson, ValidationUtil validationUtil,
                           SaleRepository saleRepository, ModelMapper modelMapper,
                           CustomerService customerService, CarService carService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.carService = carService;
    }

    @Override
    public void seed() {

        for (int i = 0; i < 30; i++) {
            Customer customer = customerService.getRandomCustomer();
            Car car = carService.getRandomCar();

            SalesSeedDto salesSeedDtos = new SalesSeedDto();
            salesSeedDtos.setCar(car);
            salesSeedDtos.setCustomer(customer);

           switch (salesSeedDtos.getCar().getMake().length()){
               case 3:
                   salesSeedDtos.setDiscount(0.05);
                   break;
               case 4:
                   salesSeedDtos.setDiscount(0.1);
                   break;
               case 5:
                   salesSeedDtos.setDiscount(0.15);
                   break;
               case 6:
                   salesSeedDtos.setDiscount(0.2);
                   break;
               case 7:
                   salesSeedDtos.setDiscount(0.3);
                   break;
               case 8:
                   salesSeedDtos.setDiscount(0.4);
                   break;
               case 9:
                   salesSeedDtos.setDiscount(0.5);
                   break;
               default:
                   salesSeedDtos.setDiscount(0.0);
           }
            boolean isValid = validationUtil.isValid(salesSeedDtos);

           if (isValid){
               Sale sale = modelMapper.map(salesSeedDtos, Sale.class);
               saleRepository.save(sale);
           }

        }

    }

    @Override
    public List<SalesByCustomerDto> getTotalSalesByCustomer() {

        return saleRepository.getTotalSalesByCustomer()
                .stream()
                .map(sale -> {
                    SalesByCustomerDto salesByCustomerDtos =
                            modelMapper.map(sale, SalesByCustomerDto.class);

                    salesByCustomerDtos.setFullName(sale.getCustomer().getName());
                    salesByCustomerDtos.setBoughtCars(sale.getCustomer().getSales().size());

                    BigDecimal price = BigDecimal.valueOf(sale.getCar().getParts()
                            .stream()
                            .mapToDouble(p -> p.getPrice().doubleValue()).sum());
                    salesByCustomerDtos.setSpentMoney(price);

                    return salesByCustomerDtos;
                })
                .sorted(Comparator.comparing(SalesByCustomerDto::getBoughtCars,
                        Comparator.reverseOrder())
                        .thenComparing(SalesByCustomerDto::getSpentMoney,
                                Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesWithAppliedDiscountDto> getSalesWithAppliedDiscount() {

        return saleRepository.getSalesWithAppliedDiscount()
                .stream()
                .map(sale -> {
                    SalesWithAppliedDiscountDto salesWithAppliedDiscountDto =
                            modelMapper.map(sale, SalesWithAppliedDiscountDto.class);

                    BigDecimal price = BigDecimal.valueOf(sale.getCar().getParts()
                            .stream()
                            .mapToDouble(p -> p.getPrice().doubleValue()).sum());
                    salesWithAppliedDiscountDto.setPrice(price);
                    BigDecimal priceWithDiscount = price
                            .multiply(BigDecimal.valueOf(1 - sale.getDiscount()));

                    salesWithAppliedDiscountDto.setPriceWithDiscount(priceWithDiscount);
                    return salesWithAppliedDiscountDto;

                }).collect(Collectors.toList());
    }
}
