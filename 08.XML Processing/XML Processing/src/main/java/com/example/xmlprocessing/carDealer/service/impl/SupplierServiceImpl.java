package com.example.xmlprocessing.carDealer.service.impl;


import com.example.xmlprocessing.carDealer.model.dto.query3.SuppliersDto;
import com.example.xmlprocessing.carDealer.model.dto.query3.SuppliersRootDto;
import com.example.xmlprocessing.carDealer.model.dto.seed.SupplierSeedRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Supplier;
import com.example.xmlprocessing.carDealer.repository.SupplierRepository;
import com.example.xmlprocessing.carDealer.service.SupplierService;
import com.example.xmlprocessing.util.ValidationUtil;

import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String PATH = "src/main/resources/file/data/suppliers.xml";

    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public SupplierServiceImpl(ValidationUtil validationUtil,
                               SupplierRepository supplierRepository,
                               ModelMapper modelMapper, XmlParser xmlParser) {

        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seed() throws JAXBException, FileNotFoundException {

        if (supplierRepository.count() > 0){
            return;
        }
        SupplierSeedRootDto supplierSeedRootDto =
                xmlParser.fromFile(PATH, SupplierSeedRootDto.class);

        supplierSeedRootDto.getSuppliers()
                .stream()
                .filter(validationUtil::isValid)
                .map(supplierSeedDto -> modelMapper
                        .map(supplierSeedDto, Supplier.class))
                .forEach(supplierRepository::save);

    }

    @Override
    public Supplier getRandomSupplier() {
        return supplierRepository.findById(ThreadLocalRandom
                .current().nextLong(1, supplierRepository.count() + 1))
                .orElse(null);
    }

    @Override
    public SuppliersRootDto getAllLocalSuppliers() {

        List<SuppliersDto> suppliersDtos = supplierRepository.getAllLocalSuppliers()
                .stream()
                .map(supplier -> {
                    SuppliersDto suppliersDto =
                            modelMapper.map(supplier, SuppliersDto.class);
                    suppliersDto.setPartsCount(supplier.getParts().size());
                    return suppliersDto;
                })
                .toList();

        SuppliersRootDto supplier = new SuppliersRootDto();
        supplier.setSupplier(suppliersDtos);
        return supplier;
    }
}
