package com.healthyme.MealPlanner.recipe.controller;

import com.healthyme.MealPlanner.common.response.ApiResponse;
import com.healthyme.MealPlanner.recipe.dto.RecipeRequest;
import com.healthyme.MealPlanner.recipe.dto.RecipeResponse;
import com.healthyme.MealPlanner.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public ApiResponse<List<RecipeResponse>> getAll() {
        return ApiResponse.success(recipeService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<RecipeResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(recipeService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RecipeResponse> create(@RequestBody RecipeRequest request) {
        return ApiResponse.success(recipeService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<RecipeResponse> update(@PathVariable Long id, @RequestBody RecipeRequest request) {
        return ApiResponse.success(recipeService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        recipeService.deleteById(id);
    }
}
