package com.healthyme.MealPlanner.recipe.dto;

import com.healthyme.MealPlanner.enums.Unit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RecipeIngredientRequest {

    private Long ingredientId;
    private BigDecimal quantity;
    private Unit unit;
}
