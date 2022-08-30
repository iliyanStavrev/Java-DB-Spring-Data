package hiberspring.repository;

import hiberspring.model.entities.Branch;
import hiberspring.model.entities.EmployeeCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeCardRepository extends JpaRepository<EmployeeCard, Long> {

    EmployeeCard findByNumber(String number);

}
