package com.example.xmlprocessing.carDealer.service;


import com.example.xmlprocessing.carDealer.model.dto.query3.SuppliersRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Supplier;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;

public interface SupplierService {

 void seed() throws JAXBException, FileNotFoundException;

    Supplier getRandomSupplier();

    SuppliersRootDto getAllLocalSuppliers();
}
