package com.example.jsonprocessing.carDealer.service;

import com.example.jsonprocessing.carDealer.model.dto.SalesByCustomerDto;
import com.example.jsonprocessing.carDealer.model.dto.SalesWithAppliedDiscountDto;
import com.example.jsonprocessing.carDealer.model.entity.Sale;

import java.util.List;

public interface SaleService {

    void seed();

    List<SalesByCustomerDto> getTotalSalesByCustomer();

    List<SalesWithAppliedDiscountDto> getSalesWithAppliedDiscount();
}
