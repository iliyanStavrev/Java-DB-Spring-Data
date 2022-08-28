package com.example.jsonprocessing.carDealer.repository;

import com.example.jsonprocessing.carDealer.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @Query("SELECT c FROM customers c " +
            "ORDER BY c.birthDate")
    List<Customer> getAllOrderByBirthDate();

}
