package entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "bank_account")
public class BankAccount extends BillingDetail{
    
    private String name;
    private String swift;

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getSwift() {
        return swift;
    }

    public void setSwift(String swift) {
        this.swift = swift;
    }
}
