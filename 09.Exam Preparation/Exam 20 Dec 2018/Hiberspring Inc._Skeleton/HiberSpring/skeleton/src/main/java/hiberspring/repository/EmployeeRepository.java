package hiberspring.repository;

import hiberspring.model.entities.Branch;
import hiberspring.model.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e " +
            "WHERE e.branch.products.size > 0 " +
            "ORDER BY concat(e.firstName,' ',e.lastName), " +
            "length(e.position) DESC")
    List<Employee> findAllProductiveEmployees();
}
