package hiberspring.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class EmployeeCard extends BaseEntity{

    private String number;

    @Column(nullable = false,unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
