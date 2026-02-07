package com.healthyme.MealPlanner.recipe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeResponse {

    private Long id;
    private String name;
    private String instructions;
    private Integer servings;
    private List<RecipeIngredientResponse> ingredients;
}
