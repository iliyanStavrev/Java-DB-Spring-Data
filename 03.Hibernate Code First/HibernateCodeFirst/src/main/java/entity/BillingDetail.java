package entity;

import javax.persistence.*;

@Entity(name = "billing_details")
@Inheritance(strategy = InheritanceType.JOINED)
public class BillingDetail extends BaseEntity{

    private String number;
    private BankUser owner;

    @Column(nullable = false,unique = true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @ManyToOne
    public BankUser getOwner() {
        return owner;
    }

    public void setOwner(BankUser owner) {
        this.owner = owner;
    }
}
