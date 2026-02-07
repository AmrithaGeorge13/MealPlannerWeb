package com.healthyme.MealPlanner.recipe.service;

import com.healthyme.MealPlanner.common.exception.ResourceNotFoundException;
import com.healthyme.MealPlanner.ingredient.service.IngredientService;
import com.healthyme.MealPlanner.recipe.dto.*;
import com.healthyme.MealPlanner.recipe.model.Recipe;
import com.healthyme.MealPlanner.recipe.model.RecipeIngredient;
import com.healthyme.MealPlanner.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientService ingredientService;

    @Transactional(readOnly = true)
    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecipeResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public Recipe getEntity(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", id));
    }

    @Transactional
    public RecipeResponse create(RecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setName(request.getName());
        recipe.setInstructions(request.getInstructions());
        recipe.setServings(request.getServings());
        if (request.getIngredients() != null) {
            for (RecipeIngredientRequest ri : request.getIngredients()) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(recipe);
                recipeIngredient.setIngredient(ingredientService.getEntity(ri.getIngredientId()));
                recipeIngredient.setQuantity(ri.getQuantity());
                recipeIngredient.setUnit(ri.getUnit());
                recipe.getIngredients().add(recipeIngredient);
            }
        }
        return toResponse(recipeRepository.save(recipe));
    }

    @Transactional
    public RecipeResponse update(Long id, RecipeRequest request) {
        Recipe existing = getEntity(id);
        existing.setName(request.getName() != null ? request.getName() : existing.getName());
        existing.setInstructions(request.getInstructions() != null ? request.getInstructions() : existing.getInstructions());
        existing.setServings(request.getServings() != null ? request.getServings() : existing.getServings());
        if (request.getIngredients() != null) {
            existing.getIngredients().clear();
            for (RecipeIngredientRequest ri : request.getIngredients()) {
                RecipeIngredient recipeIngredient = new RecipeIngredient();
                recipeIngredient.setRecipe(existing);
                recipeIngredient.setIngredient(ingredientService.getEntity(ri.getIngredientId()));
                recipeIngredient.setQuantity(ri.getQuantity());
                recipeIngredient.setUnit(ri.getUnit());
                existing.getIngredients().add(recipeIngredient);
            }
        }
        return toResponse(recipeRepository.save(existing));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe", id);
        }
        recipeRepository.deleteById(id);
    }

    private RecipeResponse toResponse(Recipe entity) {
        List<RecipeIngredientResponse> ingredients = entity.getIngredients() == null
                ? new ArrayList<>()
                : entity.getIngredients().stream()
                .map(ri -> RecipeIngredientResponse.builder()
                        .ingredientId(ri.getIngredient().getId())
                        .ingredientName(ri.getIngredient().getName())
                        .quantity(ri.getQuantity())
                        .unit(ri.getUnit())
                        .build())
                .collect(Collectors.toList());
        return RecipeResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .instructions(entity.getInstructions())
                .servings(entity.getServings())
                .ingredients(ingredients)
                .build();
    }
}
