package com.healthyme.MealPlanner.ingredient.dto;

import com.healthyme.MealPlanner.enums.Unit;
import lombok.Data;

@Data
public class IngredientRequest {

    private String name;
    private Unit defaultUnit;
}
