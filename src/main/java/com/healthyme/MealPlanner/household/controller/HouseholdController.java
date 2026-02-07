package com.healthyme.MealPlanner.household.controller;

import com.healthyme.MealPlanner.common.response.ApiResponse;
import com.healthyme.MealPlanner.household.model.Household;
import com.healthyme.MealPlanner.household.service.HouseholdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
@RequiredArgsConstructor
public class HouseholdController {

    private final HouseholdService householdService;

    @GetMapping
    public ApiResponse<List<Household>> getAll() {
        return ApiResponse.success(householdService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<Household> getById(@PathVariable Long id) {
        return ApiResponse.success(householdService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Household> create(@RequestBody Household household) {
        return ApiResponse.success(householdService.create(household));
    }

    @PutMapping("/{id}")
    public ApiResponse<Household> update(@PathVariable Long id, @RequestBody Household household) {
        return ApiResponse.success(householdService.update(id, household));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        householdService.deleteById(id);
    }
}
