package com.example.jsonprocessing.carDealer.service;

import com.example.jsonprocessing.carDealer.model.entity.Part;

import java.io.IOException;
import java.util.List;

public interface PartService {
    void seed() throws IOException;

    List<Part> getRandomListOfParts();
}
