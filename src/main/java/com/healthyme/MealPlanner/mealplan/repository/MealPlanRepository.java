package com.healthyme.MealPlanner.mealplan.repository;

import com.healthyme.MealPlanner.mealplan.model.MealPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlan, Long> {

    List<MealPlan> findByHouseholdIdAndPlanDateBetween(Long householdId, LocalDate start, LocalDate end);
}
