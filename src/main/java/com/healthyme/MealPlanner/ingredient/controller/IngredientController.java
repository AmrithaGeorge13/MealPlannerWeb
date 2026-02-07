package com.healthyme.MealPlanner.ingredient.controller;

import com.healthyme.MealPlanner.common.response.ApiResponse;
import com.healthyme.MealPlanner.ingredient.dto.IngredientRequest;
import com.healthyme.MealPlanner.ingredient.dto.IngredientResponse;
import com.healthyme.MealPlanner.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ApiResponse<List<IngredientResponse>> getAll() {
        return ApiResponse.success(ingredientService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<IngredientResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(ingredientService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<IngredientResponse> create(@RequestBody IngredientRequest request) {
        return ApiResponse.success(ingredientService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<IngredientResponse> update(@PathVariable Long id, @RequestBody IngredientRequest request) {
        return ApiResponse.success(ingredientService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        ingredientService.deleteById(id);
    }
}
