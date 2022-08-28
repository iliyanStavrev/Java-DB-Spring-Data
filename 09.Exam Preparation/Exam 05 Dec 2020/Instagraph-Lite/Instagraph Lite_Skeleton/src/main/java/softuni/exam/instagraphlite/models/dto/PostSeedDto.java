package softuni.exam.instagraphlite.models.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "post")
@XmlAccessorType(XmlAccessType.FIELD)
public class PostSeedDto {

    @XmlElement
    private String caption;
    @XmlElement
    private UsernameDto user;
    @XmlElement
    private PathDto picture;

    @NotNull
    @Size(min = 21)
    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public UsernameDto getUser() {
        return user;
    }

    public void setUser(UsernameDto user) {
        this.user = user;
    }

    public PathDto getPicture() {
        return picture;
    }

    public void setPicture(PathDto picture) {
        this.picture = picture;
    }
}
