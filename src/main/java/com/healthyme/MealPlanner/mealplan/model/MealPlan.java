package com.healthyme.MealPlanner.mealplan.model;

import com.healthyme.MealPlanner.common.model.BaseEntity;
import com.healthyme.MealPlanner.enums.MealType;
import com.healthyme.MealPlanner.household.model.Household;
import com.healthyme.MealPlanner.recipe.model.Recipe;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "meal_plans")
@Getter
@Setter
public class MealPlan extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @Column(nullable = false)
    private LocalDate planDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;
}
