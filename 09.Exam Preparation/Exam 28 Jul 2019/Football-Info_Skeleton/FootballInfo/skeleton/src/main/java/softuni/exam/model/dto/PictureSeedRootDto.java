package softuni.exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureSeedRootDto {

    private List<PictureSeedDto> picture;

    public List<PictureSeedDto> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureSeedDto> picture) {
        this.picture = picture;
    }
}
