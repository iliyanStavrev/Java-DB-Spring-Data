package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedRootDto {

    private List<OfferSeedDto> offer;

    public List<OfferSeedDto> getOffer() {
        return offer;
    }

    public void setOffer(List<OfferSeedDto> offer) {
        this.offer = offer;
    }
}
