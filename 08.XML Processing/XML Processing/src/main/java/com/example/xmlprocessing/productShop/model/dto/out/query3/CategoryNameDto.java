package com.example.xmlprocessing.productShop.model.dto.out.query3;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "category")
@XmlAccessorType(XmlAccessType.FIELD)
public class CategoryNameDto {

    @XmlAttribute(name = "name")
    private String name;
   @XmlElement
    private List<CategoryCountPriceTotalDto> info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CategoryCountPriceTotalDto> getInfo() {
        return info;
    }

    public void setInfo(List<CategoryCountPriceTotalDto> info) {
        this.info = info;
    }
}
