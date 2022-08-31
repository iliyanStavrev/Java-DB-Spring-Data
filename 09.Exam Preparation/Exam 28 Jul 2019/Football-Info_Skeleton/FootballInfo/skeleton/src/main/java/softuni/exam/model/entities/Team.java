package softuni.exam.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity(name = "teams")
public class Team extends BaseEntity{


    private String name;
    private Picture picture;
    private List<Player> players;

    @Column(length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne(optional = false)
    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    @OneToMany(mappedBy = "team",fetch = FetchType.EAGER)
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
