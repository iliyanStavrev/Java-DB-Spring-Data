package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersSeedRootDto {

    private List<SellerSeedDto> seller;

    public List<SellerSeedDto> getSellers() {
        return seller;
    }

    public void setSellers(List<SellerSeedDto> sellers) {
        this.seller = sellers;
    }
}
