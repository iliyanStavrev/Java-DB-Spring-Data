package softuni.exam.model.dto;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "picture")
@XmlAccessorType(XmlAccessType.FIELD)
public class PictureSeedDto {

    private String url;

    @NotNull
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
