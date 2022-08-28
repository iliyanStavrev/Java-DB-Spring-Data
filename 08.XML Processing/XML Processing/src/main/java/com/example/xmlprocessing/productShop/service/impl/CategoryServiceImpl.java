package com.example.xmlprocessing.productShop.service.impl;


import com.example.xmlprocessing.productShop.model.dto.out.query3.CategoryCountPriceTotalDto;
import com.example.xmlprocessing.productShop.model.dto.out.query3.CategoryNameDto;
import com.example.xmlprocessing.productShop.model.dto.out.query3.CategoryRootDto;
import com.example.xmlprocessing.productShop.model.dto.seed.CategorySeedRootDto;
import com.example.xmlprocessing.productShop.model.entity.Category;
import com.example.xmlprocessing.productShop.repository.CategoryRepository;
import com.example.xmlprocessing.productShop.service.CategoryService;

import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_FILE_PATH =
            "src/main/resources/file/data/categories.xml";


    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil, CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seed() throws IOException, JAXBException {

        if (categoryRepository.count() > 0) {
            return;
        }

        CategorySeedRootDto categorySeedRootDto =
                xmlParser.fromFile(CATEGORY_FILE_PATH, CategorySeedRootDto.class);

        categorySeedRootDto.getCategories()
                .stream()
                .filter(validationUtil::isValid)
                .map(categorySeedDto ->
                        modelMapper.map(categorySeedDto, Category.class))
                .forEach(categoryRepository::save);

    }

    @Override
    public Set<Category> getRandomCategories() {
        Set<Category> categories = new HashSet<>();
        int randomId = ThreadLocalRandom
                .current().nextInt(1, 4);
        long length = categoryRepository.count();

        for (int i = 0; i < randomId; i++) {
            long id = ThreadLocalRandom
                    .current().nextLong(1, length + 1);
            categories.add(categoryRepository.findById(id).orElse(null));
        }

        return categories;
    }

    @Override
    public CategoryRootDto getAllCategoriesByProductsCount() {

        CategoryRootDto categoryRootDto = new CategoryRootDto();
        List<CategoryNameDto> categoryNameDtos =
                categoryRepository.getAllCategoriesByProductsCount()
                        .stream()
                        .map(category -> {
                            List<CategoryCountPriceTotalDto> infoList = new ArrayList<>();
                            CategoryCountPriceTotalDto categoryCountPriceTotalDto =
                                    new CategoryCountPriceTotalDto();
                            categoryCountPriceTotalDto.setCounts(category.getProducts().size());
                            categoryCountPriceTotalDto.setAvgPrice(category.getProducts()
                                    .stream()
                                    .mapToDouble(c -> c.getPrice().doubleValue())
                                    .average()
                                    .orElse(0));
                            categoryCountPriceTotalDto.setTotalPrice(BigDecimal.valueOf(category.getProducts()
                                    .stream()
                                    .mapToDouble(c -> c.getPrice().doubleValue())
                                    .sum()));
                            infoList.add(categoryCountPriceTotalDto);
                            CategoryNameDto categoryNameDto =
                                    modelMapper.map(category, CategoryNameDto.class);
                            categoryNameDto.setInfo(infoList);
                            return categoryNameDto;
                        }).toList();

        categoryRootDto.setCategories(categoryNameDtos);

        return categoryRootDto;
    }
}
