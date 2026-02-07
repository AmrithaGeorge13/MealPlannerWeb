package com.healthyme.MealPlanner.recipe.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeRequest {

    private String name;
    private String instructions;
    private Integer servings;
    private List<RecipeIngredientRequest> ingredients;
}
