package softuni.exam.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamSeedRootDto {

    private List<TeamSeedDto> team;

    public List<TeamSeedDto> getTeam() {
        return team;
    }

    public void setTeam(List<TeamSeedDto> team) {
        this.team = team;
    }
}
