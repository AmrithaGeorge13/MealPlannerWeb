
package com.healthyme.MealPlanner.mealplan.service;

import com.healthyme.MealPlanner.common.exception.ResourceNotFoundException;
import com.healthyme.MealPlanner.household.repository.HouseholdRepository;
import com.healthyme.MealPlanner.mealplan.dto.MealPlanRequest;
import com.healthyme.MealPlanner.mealplan.dto.MealPlanResponse;
import com.healthyme.MealPlanner.mealplan.model.MealPlan;
import com.healthyme.MealPlanner.mealplan.repository.MealPlanRepository;
import com.healthyme.MealPlanner.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MealPlanService {

    private final MealPlanRepository mealPlanRepository;
    private final HouseholdRepository householdRepository;
    private final RecipeService recipeService;

    @Transactional(readOnly = true)
    public List<MealPlanResponse> findAll() {
        return mealPlanRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MealPlanResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public List<MealPlanResponse> findByHouseholdAndDateRange(Long householdId, LocalDate start, LocalDate end) {
        return mealPlanRepository.findByHouseholdIdAndPlanDateBetween(householdId, start, end).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MealPlan getEntity(Long id) {
        return mealPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MealPlan", id));
    }

    @Transactional
    public MealPlanResponse create(MealPlanRequest request) {
        MealPlan mealPlan = new MealPlan();
        mealPlan.setHousehold(householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new ResourceNotFoundException("Household", request.getHouseholdId())));
        mealPlan.setPlanDate(request.getPlanDate());
        mealPlan.setMealType(request.getMealType());
        mealPlan.setRecipe(recipeService.getEntity(request.getRecipeId()));
        return toResponse(mealPlanRepository.save(mealPlan));
    }

    @Transactional
    public MealPlanResponse update(Long id, MealPlanRequest request) {
        MealPlan existing = getEntity(id);
        if (request.getHouseholdId() != null) {
            existing.setHousehold(householdRepository.findById(request.getHouseholdId())
                    .orElseThrow(() -> new ResourceNotFoundException("Household", request.getHouseholdId())));
        }
        if (request.getPlanDate() != null) existing.setPlanDate(request.getPlanDate());
        if (request.getMealType() != null) existing.setMealType(request.getMealType());
        if (request.getRecipeId() != null) existing.setRecipe(recipeService.getEntity(request.getRecipeId()));
        return toResponse(mealPlanRepository.save(existing));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!mealPlanRepository.existsById(id)) {
            throw new ResourceNotFoundException("MealPlan", id);
        }
        mealPlanRepository.deleteById(id);
    }

    private MealPlanResponse toResponse(MealPlan entity) {
        return MealPlanResponse.builder()
                .id(entity.getId())
                .householdId(entity.getHousehold().getId())
                .planDate(entity.getPlanDate())
                .mealType(entity.getMealType())
                .recipeId(entity.getRecipe().getId())
                .recipeName(entity.getRecipe().getName())
                .build();
    }
}
