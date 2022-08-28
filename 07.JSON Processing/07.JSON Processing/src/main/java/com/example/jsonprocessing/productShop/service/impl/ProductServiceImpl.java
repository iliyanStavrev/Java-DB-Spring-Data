package com.example.jsonprocessing.productShop.service.impl;

import com.example.jsonprocessing.productShop.model.dto.ProductNameAndPriceAndSellerDto;
import com.example.jsonprocessing.productShop.model.dto.ProductSeedDto;
import com.example.jsonprocessing.productShop.model.entity.Product;
import com.example.jsonprocessing.productShop.repository.ProductRepository;
import com.example.jsonprocessing.productShop.service.CategoryService;
import com.example.jsonprocessing.productShop.service.ProductService;
import com.example.jsonprocessing.productShop.service.UserService;
import com.example.jsonprocessing.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final String PATH = "src/main/resources/files/data/products.json";

    private final Gson gson;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(Gson gson, ProductRepository productRepository,
                              ModelMapper modelMapper,
                              ValidationUtil validationUtil, UserService userService, CategoryService categoryService) {
        this.gson = gson;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seed() throws IOException {

        if (productRepository.count() > 0) {
            return;
        }

        String fileContent = Files.readString(Path.of(PATH));

        ProductSeedDto[] productSeedDtos = gson
                .fromJson(fileContent, ProductSeedDto[].class);

        Arrays.stream(productSeedDtos)
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product = modelMapper.map(productSeedDto, Product.class);
                    product.setSeller(userService.getRandomUser());
                    if (product.getPrice().compareTo(BigDecimal.valueOf(850)) > 0){
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setCategories(categoryService.getRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public List<ProductNameAndPriceAndSellerDto> findProductsInRange(BigDecimal price1, BigDecimal price2) {

        return this.productRepository
                .findAllByPriceBetweenAndBuyerIsNull(price1,price2)
                .stream()
                .map(product -> {
                    ProductNameAndPriceAndSellerDto productNameAndPriceDto =
                            modelMapper.map(product, ProductNameAndPriceAndSellerDto.class);

                    productNameAndPriceDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));
                    return productNameAndPriceDto;
                }).collect(Collectors.toList());
    }
}
