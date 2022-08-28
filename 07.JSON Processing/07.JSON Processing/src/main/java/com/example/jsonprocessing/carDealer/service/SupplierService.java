package com.example.jsonprocessing.carDealer.service;

import com.example.jsonprocessing.carDealer.model.dto.SupplierIdNamePartsDto;
import com.example.jsonprocessing.carDealer.model.entity.Supplier;

import java.io.IOException;
import java.util.List;

public interface SupplierService {

    void seed() throws IOException;

    Supplier getRandomSupplier();

    List<SupplierIdNamePartsDto> getAllLocalSuppliers();
}
