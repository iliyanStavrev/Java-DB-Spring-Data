package hiberspring.model.dtos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedRootDto {

    private List<ProductSeedDto> product;

    public List<ProductSeedDto> getProduct() {
        return product;
    }

    public void setProduct(List<ProductSeedDto> product) {
        this.product = product;
    }
}
