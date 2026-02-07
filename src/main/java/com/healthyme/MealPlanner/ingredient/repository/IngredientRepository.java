package com.healthyme.MealPlanner.ingredient.repository;

import com.healthyme.MealPlanner.ingredient.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    List<Ingredient> findByNameContainingIgnoreCase(String name);
}
