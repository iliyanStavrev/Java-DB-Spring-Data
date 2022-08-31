package softuni.exam.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "pictures")
public class Picture extends BaseEntity{

    private String url;

    @Column(nullable = false)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
