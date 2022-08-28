package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "planes")
@XmlAccessorType(XmlAccessType.FIELD)
public class PlaneSeedRootDto {

    private List<PlaneSeedDto> plane;

    public List<PlaneSeedDto> getPlane() {
        return plane;
    }

    public void setPlane(List<PlaneSeedDto> plane) {
        this.plane = plane;
    }
}
