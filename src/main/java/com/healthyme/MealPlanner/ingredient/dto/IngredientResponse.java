package com.healthyme.MealPlanner.ingredient.dto;

import com.healthyme.MealPlanner.enums.Unit;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredientResponse {

    private Long id;
    private String name;
    private Unit defaultUnit;
}
