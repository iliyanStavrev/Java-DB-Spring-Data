package com.example.xmlprocessing.productShop.model.dto.out.query3;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "categories")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryRootDto {

    List<CategoryNameDto> categories;

    public List<CategoryNameDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryNameDto> categories) {
        this.categories = categories;
    }
}
