package hiberspring.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Town extends BaseEntity{

    private String name;
    private Integer population;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}
