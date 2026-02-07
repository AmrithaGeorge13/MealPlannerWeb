package com.healthyme.MealPlanner.mealplan.dto;

import com.healthyme.MealPlanner.enums.MealType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MealPlanRequest {

    private Long householdId;
    private LocalDate planDate;
    private MealType mealType;
    private Long recipeId;
}
