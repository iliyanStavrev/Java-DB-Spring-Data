package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.model.entities.Picture;
import softuni.exam.model.entities.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team,Long> {

    Team findByName(String name);

}
