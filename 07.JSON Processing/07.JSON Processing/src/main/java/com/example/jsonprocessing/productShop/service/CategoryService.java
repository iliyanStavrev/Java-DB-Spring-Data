package com.example.jsonprocessing.productShop.service;


import com.example.jsonprocessing.productShop.model.dto.CategoriesByProductsDto;
import com.example.jsonprocessing.productShop.model.entity.Category;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seed() throws IOException;

    Set<Category> getRandomCategories();

    List<CategoriesByProductsDto> getAllCategoriesByProductsCount();
}
