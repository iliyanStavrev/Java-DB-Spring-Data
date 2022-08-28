package com.example.advquerying.repository;

import com.example.advquerying.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepo extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findAllByNameStartingWith(char symbol);
}
