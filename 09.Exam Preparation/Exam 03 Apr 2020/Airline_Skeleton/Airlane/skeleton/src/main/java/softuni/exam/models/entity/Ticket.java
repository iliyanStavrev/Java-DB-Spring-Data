package softuni.exam.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "tickets")
public class Ticket extends BaseEntity{

    private String serialNumber;
    private BigDecimal price;
    private LocalDateTime takeoff;
    private Town from;
    private Town to;
    private Passenger passenger;
    private Plane plane;

    @Column(name = "serial_number",unique = true)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(LocalDateTime takeoff) {
        this.takeoff = takeoff;
    }

    @ManyToOne
    public Town getFrom() {
        return from;
    }

    public void setFrom(Town from) {
        this.from = from;
    }

    @ManyToOne
    public Town getTo() {
        return to;
    }

    public void setTo(Town to) {
        this.to = to;
    }

    @ManyToOne
    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    @ManyToOne
    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }
}
