package com.example.jsonprocessing.productShop.repository;

import com.example.jsonprocessing.productShop.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByPriceBetweenAndBuyerIsNull(BigDecimal price1, BigDecimal price2);
}
