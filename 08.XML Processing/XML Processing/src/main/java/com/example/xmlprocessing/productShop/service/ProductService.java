package com.example.xmlprocessing.productShop.service;



import com.example.xmlprocessing.productShop.model.dto.out.query1.ProductInRangeRootDto;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;

public interface ProductService {

     void seed() throws IOException, JAXBException;

    ProductInRangeRootDto findProductsInRange(BigDecimal price1, BigDecimal price2);
}
