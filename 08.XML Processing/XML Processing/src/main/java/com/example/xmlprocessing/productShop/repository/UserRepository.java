package com.example.xmlprocessing.productShop.repository;

import com.example.xmlprocessing.productShop.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query("SELECT u FROM users u " +
            "JOIN products p ON p.seller.id = u.id " +
            "WHERE p.buyer IS NOT NULL " +
            "group by u.id " +
            "ORDER BY u.lastName, u.firstName")
    List<User> findAllBySoldProductsSize();

    @Query("SELECT u FROM users u " +
            "WHERE (SELECT COUNT(p) FROM products p WHERE p.seller.id = u.id) > 0 " +
            "ORDER BY u.soldProducts.size")
    List<User> getAllUsersWithAtLeastOneProductSold();
}
