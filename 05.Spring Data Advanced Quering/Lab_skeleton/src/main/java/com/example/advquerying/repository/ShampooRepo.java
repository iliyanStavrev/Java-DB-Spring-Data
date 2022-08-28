package com.example.advquerying.repository;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooRepo extends JpaRepository<Shampoo, Long> {
    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Integer countAllByPriceLessThan(BigDecimal price);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i WHERE i.name IN :names")
    List<Shampoo> findAllByIngredientsNames(List<String> names);

    @Query("SELECT s FROM Shampoo s JOIN s.ingredients i " +
            "GROUP BY s.id HAVING COUNT(i) < :number")
    List<Shampoo> findAllByIngredientsLessThan(long number);


    @Query("DELETE FROM Ingredient i WHERE i.name = :name")
    @Modifying
   void deleteAllByName(String name);

    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1")
    @Modifying
    void updateIngredientPrice();


    @Query("UPDATE Ingredient i SET i.price = i.price * 1.1 WHERE i.name IN :names")
    @Modifying
    void updateIngredientPriceByNames(List<String>names);
}
