package com.healthyme.MealPlanner.recipe.dto;

import com.healthyme.MealPlanner.enums.Unit;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class RecipeIngredientResponse {

    private Long ingredientId;
    private String ingredientName;
    private BigDecimal quantity;
    private Unit unit;
}
