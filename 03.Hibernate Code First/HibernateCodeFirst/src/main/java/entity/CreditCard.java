package entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "credit_card")
public class CreditCard extends BillingDetail{

    private String cardType;
    private int expMonth;
    private int expYear;

    @Column(name = "card_type")
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "exp_month")
    public int getExpMonth() {
        return expMonth;
    }

    public void setExpMonth(int expMonth) {
        this.expMonth = expMonth;
    }

    @Column(name = "exp_year")
    public int getExpYear() {
        return expYear;
    }

    public void setExpYear(int expYear) {
        this.expYear = expYear;
    }
}
