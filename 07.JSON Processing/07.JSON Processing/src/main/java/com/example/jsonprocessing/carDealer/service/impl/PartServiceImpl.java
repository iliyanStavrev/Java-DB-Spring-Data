package com.example.jsonprocessing.carDealer.service.impl;

import com.example.jsonprocessing.carDealer.model.dto.PartsSeedDto;
import com.example.jsonprocessing.carDealer.model.entity.Part;
import com.example.jsonprocessing.carDealer.repository.PartRepository;
import com.example.jsonprocessing.carDealer.service.PartService;
import com.example.jsonprocessing.carDealer.service.SupplierService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private static final String PATH = "src/main/resources/files/data/parts.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;

    public PartServiceImpl(Gson gson, ValidationUtil validationUtil,
                           PartRepository partRepository,
                           ModelMapper modelMapper, SupplierService supplierService) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
    }


    @Override
    public void seed() throws IOException {

        if (partRepository.count() > 0){
            return;
        }

        PartsSeedDto[] partsSeedDto = gson.fromJson(Files.
                readString(Path.of(PATH)) , PartsSeedDto[].class);

        Arrays.stream(partsSeedDto)
                .filter(validationUtil::isValid)
                .map(partSeed -> {
                Part part = modelMapper.map(partSeed,Part.class);
                part.setSupplier(supplierService.getRandomSupplier());
                return part;
                })
                .forEach(partRepository::save);

    }

    @Override
    public List<Part> getRandomListOfParts() {

        List<Part> parts = new ArrayList<>();
        int size = ThreadLocalRandom.current().nextInt(3,5);
        Long count = partRepository.count() + 1;

        for (int i = 0; i < size; i++) {
            Long id = ThreadLocalRandom.current().nextLong(1,count);

            parts.add(partRepository.findById(id).orElse(null));
        }
        return parts;
    }
}
