package com.example.jsonprocessing.productShop.service.impl;

import com.example.jsonprocessing.productShop.model.dto.CategoriesByProductsDto;
import com.example.jsonprocessing.productShop.model.dto.CategorySeedDto;
import com.example.jsonprocessing.productShop.model.entity.Category;
import com.example.jsonprocessing.productShop.repository.CategoryRepository;
import com.example.jsonprocessing.productShop.service.CategoryService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String PATH = "src/main/resources/files/data/categories.json";

    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(Gson gson, ValidationUtil validationUtil,
                               CategoryRepository categoryRepository,
                               ModelMapper modelMapper) {
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seed() throws IOException {

        if (categoryRepository.count() > 0){
            return;
        }
        String fileInput = Files.readString(Path.of(PATH));

        CategorySeedDto[] categorySeedDtos = gson
                .fromJson(fileInput,CategorySeedDto[].class);

        Arrays.stream(categorySeedDtos)
                .filter(validationUtil::isValid)
                .map(categorySeedDto -> modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);
    }

    @Override
    public Set<Category> getRandomCategories() {

        Set<Category> categories = new HashSet<>();
        int randomId = ThreadLocalRandom
                .current().nextInt(1,4);
        long length = categoryRepository.count();

        for (int i = 0; i < randomId; i++) {
            long id = ThreadLocalRandom
                    .current().nextLong(1,length + 1);
            categories.add(categoryRepository.findById(id).orElse(null));
        }

        return categories;
    }

    @Override
    public List<CategoriesByProductsDto> getAllCategoriesByProductsCount() {
                return this.categoryRepository
                .getAllCategoriesByProductsCount()
                .stream()
                .map(category -> {
                    CategoriesByProductsDto categoriesByProductsDto =
                            modelMapper.map(category, CategoriesByProductsDto.class);
                    categoriesByProductsDto.setProductsCount(category.getProducts().size());
                    double avg = category.getProducts()
                            .stream().mapToDouble(p -> p.getPrice().doubleValue()).average().orElse(0);
                    categoriesByProductsDto.setAveragePrice(avg);
                    categoriesByProductsDto.setTotalRevenue(BigDecimal.valueOf(category.getProducts()
                            .stream().mapToDouble(p -> p.getPrice().doubleValue()).sum()));

                    return categoriesByProductsDto;
                })
                .collect(Collectors.toList());
    }
}
