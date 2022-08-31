package softuni.exam.model.dto;

import com.google.gson.annotations.Expose;

public class PictureUrlDto {

    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
