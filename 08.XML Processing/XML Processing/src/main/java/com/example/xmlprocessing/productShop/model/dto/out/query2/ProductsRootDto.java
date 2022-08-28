package com.example.xmlprocessing.productShop.model.dto.out.query2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductsRootDto {

    @XmlElement(name = "product")
    private List<ProductsNamePriceBuyerDto> products;

    public List<ProductsNamePriceBuyerDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductsNamePriceBuyerDto> products) {
        this.products = products;
    }
}
