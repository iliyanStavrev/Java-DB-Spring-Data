package hiberspring.model.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;

public class BranchSeedDto {

    @Expose
    private String name;
    @Expose
    private String town;

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
