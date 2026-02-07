package com.healthyme.MealPlanner.mealplan.dto;

import com.healthyme.MealPlanner.enums.MealType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MealPlanResponse {

    private Long id;
    private Long householdId;
    private LocalDate planDate;
    private MealType mealType;
    private Long recipeId;
    private String recipeName;
}
