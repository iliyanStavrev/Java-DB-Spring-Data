package com.example.xmlprocessing.carDealer.repository;


import com.example.xmlprocessing.carDealer.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Long> {

    @Query("SELECT s FROM suppliers s " +
            "WHERE s.importer = false ")
    List<Supplier> getAllLocalSuppliers();
}
