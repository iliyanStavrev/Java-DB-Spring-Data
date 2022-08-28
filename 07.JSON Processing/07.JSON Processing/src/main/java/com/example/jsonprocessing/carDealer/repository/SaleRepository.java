package com.example.jsonprocessing.carDealer.repository;

import com.example.jsonprocessing.carDealer.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {

    @Query("SELECT s FROM sales s " +
            "GROUP BY s.customer " +
            "HAVING COUNT(s.customer) > 0")
    List<Sale> getTotalSalesByCustomer();

    @Query("SELECT s FROM sales s")
    List<Sale> getSalesWithAppliedDiscount();
}
