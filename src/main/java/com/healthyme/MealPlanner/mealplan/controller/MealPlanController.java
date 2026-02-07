package com.healthyme.MealPlanner.mealplan.controller;

import com.healthyme.MealPlanner.common.response.ApiResponse;
import com.healthyme.MealPlanner.mealplan.dto.MealPlanRequest;
import com.healthyme.MealPlanner.mealplan.dto.MealPlanResponse;
import com.healthyme.MealPlanner.mealplan.service.MealPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/meal-plans")
@RequiredArgsConstructor
public class MealPlanController {

    private final MealPlanService mealPlanService;

    @GetMapping
    public ApiResponse<List<MealPlanResponse>> getAll(
            @RequestParam(required = false) Long householdId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        if (householdId != null && start != null && end != null) {
            return ApiResponse.success(mealPlanService.findByHouseholdAndDateRange(householdId, start, end));
        }
        return ApiResponse.success(mealPlanService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MealPlanResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(mealPlanService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MealPlanResponse> create(@RequestBody MealPlanRequest request) {
        return ApiResponse.success(mealPlanService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<MealPlanResponse> update(@PathVariable Long id, @RequestBody MealPlanRequest request) {
        return ApiResponse.success(mealPlanService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        mealPlanService.deleteById(id);
    }
}
