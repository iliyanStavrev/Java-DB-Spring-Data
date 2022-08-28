package com.example.jsonprocessing.carDealer.service.impl;

import com.example.jsonprocessing.carDealer.model.dto.SupplierIdNamePartsDto;
import com.example.jsonprocessing.carDealer.model.dto.SuppliersSeedDto;
import com.example.jsonprocessing.carDealer.model.entity.Supplier;
import com.example.jsonprocessing.carDealer.repository.SupplierRepository;
import com.example.jsonprocessing.carDealer.service.SupplierService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {
    private static final String PATH = "src/main/resources/files/data/suppliers.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;

    public SupplierServiceImpl(Gson gson, ValidationUtil validationUtil,
                               SupplierRepository supplierRepository,
                               ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seed() throws IOException {

        if (supplierRepository.count() > 0){
            return;
        }
        String input = Files.readString(Path.of(PATH));

        SuppliersSeedDto[] suppliersSeedDto = gson.fromJson(input,SuppliersSeedDto[].class);

        Arrays.stream(suppliersSeedDto)
                .filter(validationUtil::isValid)
                .map(supplier-> modelMapper.map(supplier, Supplier.class))
                .forEach(supplierRepository::save);

    }

    @Override
    public Supplier getRandomSupplier() {

        long id = ThreadLocalRandom.current().nextLong(
                1,supplierRepository.count() + 1);

        return supplierRepository.findById(id).orElse(null);
    }

    @Override
    public List<SupplierIdNamePartsDto> getAllLocalSuppliers() {

        return supplierRepository.getAllLocalSuppliers()
                .stream()
                .map(s -> {
                    SupplierIdNamePartsDto supplierIdNamePartsDto =
                            modelMapper.map(s,SupplierIdNamePartsDto.class);
                    supplierIdNamePartsDto.setPartsCount(s.getParts().size());
                    return supplierIdNamePartsDto;
                })
                .collect(Collectors.toList());
    }
}
