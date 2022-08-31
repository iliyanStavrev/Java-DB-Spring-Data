package softuni.exam.model.dto;

import com.google.gson.annotations.Expose;

public class TeamNameDto {

    @Expose
    private String name;
    @Expose
    private PictureUrlDto picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureUrlDto getPicture() {
        return picture;
    }

    public void setPicture(PictureUrlDto picture) {
        this.picture = picture;
    }
}
