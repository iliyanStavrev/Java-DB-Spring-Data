package com.example.jsonprocessing.productShop.service;

import com.example.jsonprocessing.productShop.model.dto.ProductNameAndPriceAndSellerDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

     void seed() throws IOException;

     List<ProductNameAndPriceAndSellerDto> findProductsInRange(BigDecimal price1, BigDecimal price2);
}
