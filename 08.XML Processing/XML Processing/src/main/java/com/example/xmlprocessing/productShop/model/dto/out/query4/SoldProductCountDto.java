package com.example.xmlprocessing.productShop.model.dto.out.query4;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "sold-products")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoldProductCountDto {

    @XmlAttribute(name = "count")
    private Integer count;
    @XmlElement(name = "product")
    List<ProductNamePriceDto> products;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ProductNamePriceDto> getProducts() {
        return products;
    }

    public void setProducts(List<ProductNamePriceDto> products) {
        this.products = products;
    }
}
