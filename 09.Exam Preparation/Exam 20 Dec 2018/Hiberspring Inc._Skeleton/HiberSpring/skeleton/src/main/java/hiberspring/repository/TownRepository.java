package hiberspring.repository;

import hiberspring.model.entities.Branch;
import hiberspring.model.entities.Town;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TownRepository extends JpaRepository<Town, Long> {

    Town findByName(String name);

}
