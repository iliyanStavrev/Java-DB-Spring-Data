package com.example.xmlprocessing.carDealer.service.impl;


import com.example.xmlprocessing.carDealer.model.dto.seed.PartSeedDto;
import com.example.xmlprocessing.carDealer.model.dto.seed.PartSeedRootDto;
import com.example.xmlprocessing.carDealer.model.entity.Part;
import com.example.xmlprocessing.carDealer.repository.PartRepository;
import com.example.xmlprocessing.carDealer.service.PartService;
import com.example.xmlprocessing.carDealer.service.SupplierService;
import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PartServiceImpl implements PartService {

    private static final String PATH = "src/main/resources/file/data/parts.xml";


    private final ValidationUtil validationUtil;
    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final SupplierService supplierService;
    private final XmlParser xmlParser;

    public PartServiceImpl(ValidationUtil validationUtil,
                           PartRepository partRepository,
                           ModelMapper modelMapper, SupplierService supplierService, XmlParser xmlParser) {

        this.validationUtil = validationUtil;
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.supplierService = supplierService;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seed() throws JAXBException, FileNotFoundException {

        if (partRepository.count() > 0){
            return;
        }
        PartSeedRootDto partSeedRootDto = xmlParser
                .fromFile(PATH, PartSeedRootDto.class);

        partSeedRootDto.getParts()
                .stream()
                .filter(validationUtil::isValid)
                .map(partSeedDto ->{
                   Part part = modelMapper.map(partSeedDto, Part.class);
                    part.setSupplier(supplierService.getRandomSupplier());
                   return part;
                })
                .forEach(partRepository::save);
    }

    @Override
    public List<Part> getRandomListOfParts() {
        List<Part> parts = new ArrayList<>();
        int size = ThreadLocalRandom.current().nextInt(10,20);
        Long count = partRepository.count() + 1;

        for (int i = 0; i < size; i++) {
            Long id = ThreadLocalRandom.current().nextLong(1,count);

            parts.add(partRepository.findById(id).orElse(null));
        }
        return parts;
    }
}