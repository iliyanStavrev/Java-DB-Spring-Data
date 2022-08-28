package softuni.exam.models.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@XmlRootElement(name = "ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketSeedDto {

    @XmlElement(name = "serial-number")
    private String serialNumber;
    private BigDecimal price;
    @XmlElement(name = "take-off")
    private String takeoff;
    @XmlElement(name = "from-town")
    private FromTownDto fromTown;
    @XmlElement(name = "to-town")
    private ToTownDto toTown;
    private EmailDto passenger;
    private RegisterDto plane;

    @Size(min = 2)
    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Positive
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTakeoff() {
        return takeoff;
    }

    public void setTakeoff(String takeoff) {
        this.takeoff = takeoff;
    }

    public FromTownDto getFromTown() {
        return fromTown;
    }

    public void setFromTown(FromTownDto fromTown) {
        this.fromTown = fromTown;
    }

    public ToTownDto getToTown() {
        return toTown;
    }

    public void setToTown(ToTownDto toTown) {
        this.toTown = toTown;
    }

    public EmailDto getPassenger() {
        return passenger;
    }

    public void setPassenger(EmailDto passenger) {
        this.passenger = passenger;
    }

    public RegisterDto getPlane() {
        return plane;
    }

    public void setPlane(RegisterDto plane) {
        this.plane = plane;
    }
}
