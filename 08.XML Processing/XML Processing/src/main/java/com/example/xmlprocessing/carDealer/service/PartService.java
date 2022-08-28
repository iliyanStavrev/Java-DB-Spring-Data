package com.example.xmlprocessing.carDealer.service;


import com.example.xmlprocessing.carDealer.model.entity.Part;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.List;

public interface PartService {

    void seed() throws JAXBException, FileNotFoundException;

    List<Part> getRandomListOfParts();

}
