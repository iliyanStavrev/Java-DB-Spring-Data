package com.example.advquerying.service;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {
    List<String> findAllBySizeOrderById(Size size);

    List<String> findAllBySizeOrLabelIdOrderByPrice(Size size, Long id);

    List<String> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    List<String> findAllByNameStartingWith(char symbol);

    Integer countAllByPriceLessThan(BigDecimal price);

    List<String> findAllByIngredientsNames(List<String> names);

    List<String> findAllByIngredientsLessThan(long number);

    void deleteAllByName(String name);

    void updateIngredientPrice();

    void updateIngredientPriceByNames(List<String>names);
}
