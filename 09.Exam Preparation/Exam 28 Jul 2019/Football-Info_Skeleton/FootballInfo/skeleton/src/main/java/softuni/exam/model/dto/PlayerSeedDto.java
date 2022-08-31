package softuni.exam.model.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.model.entities.Picture;
import softuni.exam.model.entities.Team;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class PlayerSeedDto {

    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer number;
    @Expose
    private BigDecimal salary;
    @Expose
    private String position;
    @Expose
    private PictureUrlDto picture;
    @Expose
    private TeamNameDto team;

    @NotNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 3,max = 15)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Min(1)
    @Max(99)
    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    @Min(0)
    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Size(max = 2)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @NotNull
    public PictureUrlDto getPicture() {
        return picture;
    }

    public void setPicture(PictureUrlDto picture) {
        this.picture = picture;
    }

    @NotNull
    public TeamNameDto getTeam() {
        return team;
    }

    public void setTeam(TeamNameDto team) {
        this.team = team;
    }
}
