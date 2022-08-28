package com.example.xmlprocessing.productShop.service.impl;


import com.example.xmlprocessing.productShop.model.dto.out.query1.ProductInRangeDto;
import com.example.xmlprocessing.productShop.model.dto.out.query1.ProductInRangeRootDto;
import com.example.xmlprocessing.productShop.model.dto.seed.ProductSeedRootDto;
import com.example.xmlprocessing.productShop.model.entity.Product;
import com.example.xmlprocessing.productShop.repository.ProductRepository;
import com.example.xmlprocessing.productShop.service.CategoryService;
import com.example.xmlprocessing.productShop.service.ProductService;
import com.example.xmlprocessing.productShop.service.UserService;
import com.example.xmlprocessing.util.ValidationUtil;
import com.example.xmlprocessing.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final String FILE_PATH =
            "src/main/resources/file/data/products.xml";


    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductServiceImpl(XmlParser xmlParser, ValidationUtil validationUtil,
                              ModelMapper modelMapper, ProductRepository productRepository, UserService userService, CategoryService categoryService) {
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @Override
    public void seed() throws IOException, JAXBException {

        ProductSeedRootDto productSeedRootDto =
                xmlParser.fromFile(FILE_PATH, ProductSeedRootDto.class);

        productSeedRootDto.getProducts()
                .stream()
                .filter(validationUtil::isValid)
                .map(productSeedDto -> {
                    Product product =
                    modelMapper.map(productSeedDto, Product.class);
                    if (productSeedDto.getPrice().compareTo(BigDecimal.valueOf(750L)) < 0){
                        product.setBuyer(userService.getRandomUser());
                    }
                    product.setSeller(userService.getRandomUser());
                    product.setCategories(categoryService.getRandomCategories());
                    return product;
                })
                .forEach(productRepository::save);
    }

    @Override
    public ProductInRangeRootDto findProductsInRange(BigDecimal price1, BigDecimal price2) {

        ProductInRangeRootDto productInRangeRootDto = new ProductInRangeRootDto();
      productInRangeRootDto.setProducts(productRepository
                .findAllByPriceBetweenAndBuyerIsNullOrderByPrice(price1, price2)
                .stream()
                .map(product -> {
                    ProductInRangeDto productInRangeDto =
                            modelMapper.map(product, ProductInRangeDto.class);
                    productInRangeDto.setSeller(String.format("%s %s",
                            product.getSeller().getFirstName(),
                            product.getSeller().getLastName()));

                    return productInRangeDto;
                }).collect(Collectors.toList()));

      return productInRangeRootDto;
    }
}
