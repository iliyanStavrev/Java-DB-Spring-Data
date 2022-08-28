package com.example.xmlprocessing.productShop.service;



import com.example.xmlprocessing.productShop.model.dto.out.query3.CategoryRootDto;
import com.example.xmlprocessing.productShop.model.entity.Category;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CategoryService {

    void seed() throws IOException, JAXBException;

    Set<Category> getRandomCategories();

    CategoryRootDto getAllCategoriesByProductsCount();


}
