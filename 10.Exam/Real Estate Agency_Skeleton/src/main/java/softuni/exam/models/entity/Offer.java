package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity(name = "offers")
public class Offer extends BaseEntity{

    private BigDecimal price;
    private LocalDate publishedOn;
    private Apartment apartment;
    private Agent agent;

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Column(name = "published_on",nullable = false)
    public LocalDate getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(LocalDate publishedOn) {
        this.publishedOn = publishedOn;
    }

    @ManyToOne(optional = false)
    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    @ManyToOne(optional = false)
    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
}
