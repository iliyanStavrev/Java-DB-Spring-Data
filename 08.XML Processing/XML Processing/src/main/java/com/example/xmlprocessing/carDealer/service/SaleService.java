package com.example.xmlprocessing.carDealer.service;



import com.example.xmlprocessing.carDealer.model.dto.query5.CustomersSalesRootDto;
import com.example.xmlprocessing.carDealer.model.dto.query6.SalesRootDto;

import java.util.List;

public interface SaleService {

    void seed();

    CustomersSalesRootDto getTotalSalesByCustomer();

    SalesRootDto getSalesWithAppliedDiscount();

}
