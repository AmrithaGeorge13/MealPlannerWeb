package com.healthyme.MealPlanner.ingredient.service;

import com.healthyme.MealPlanner.common.exception.ResourceNotFoundException;
import com.healthyme.MealPlanner.ingredient.dto.IngredientRequest;
import com.healthyme.MealPlanner.ingredient.dto.IngredientResponse;
import com.healthyme.MealPlanner.ingredient.model.Ingredient;
import com.healthyme.MealPlanner.ingredient.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<IngredientResponse> findAll() {
        return ingredientRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public IngredientResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public Ingredient getEntity(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient", id));
    }

    @Transactional
    public List<IngredientResponse> create(List<IngredientRequest> requests) {
        List<IngredientResponse> ingredientResponses = new ArrayList<>();
        for (IngredientRequest ingredientRequest : requests) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientRequest.getName());
            ingredient.setDefaultUnit(ingredientRequest.getDefaultUnit());
            ingredient = ingredientRepository.save(ingredient);
            ingredientResponses.add(toResponse(ingredient));
        }
        return ingredientResponses;
    }

    @Transactional
    public IngredientResponse update(Long id, IngredientRequest request) {
        Ingredient existing = getEntity(id);
        if (request.getName() != null) existing.setName(request.getName());
        if (request.getDefaultUnit() != null) existing.setDefaultUnit(request.getDefaultUnit());
        return toResponse(ingredientRepository.save(existing));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Ingredient", id);
        }
        ingredientRepository.deleteById(id);
    }

    private IngredientResponse toResponse(Ingredient entity) {
        return IngredientResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .defaultUnit(entity.getDefaultUnit())
                .build();
    }
}
