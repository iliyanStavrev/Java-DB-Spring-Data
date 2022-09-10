package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Picture;

import java.time.LocalDate;
import java.util.List;

public class CarExportDto {

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Long kilometers;
    @Expose
    private String registeredOn;
    @Expose
    private Integer numberOfPictures;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getKilometers() {
        return kilometers;
    }

    public void setKilometers(Long kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }

    public Integer getNumberOfPictures() {
        return numberOfPictures;
    }

    public void setNumberOfPictures(Integer numberOfPictures) {
        this.numberOfPictures = numberOfPictures;
    }
}
