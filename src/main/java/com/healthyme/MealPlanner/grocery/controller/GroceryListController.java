package com.healthyme.MealPlanner.grocery.controller;

import com.healthyme.MealPlanner.common.response.ApiResponse;
import com.healthyme.MealPlanner.grocery.dto.GroceryListRequest;
import com.healthyme.MealPlanner.grocery.dto.GroceryListResponse;
import com.healthyme.MealPlanner.grocery.service.GroceryListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grocery-lists")
@RequiredArgsConstructor
public class GroceryListController {

    private final GroceryListService groceryListService;

    @GetMapping
    public ApiResponse<List<GroceryListResponse>> getAll(@RequestParam(required = false) Long householdId) {
        if (householdId != null) {
            return ApiResponse.success(groceryListService.findByHouseholdId(householdId));
        }
        return ApiResponse.success(groceryListService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<GroceryListResponse> getById(@PathVariable Long id) {
        return ApiResponse.success(groceryListService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<GroceryListResponse> create(@RequestBody GroceryListRequest request) {
        return ApiResponse.success(groceryListService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<GroceryListResponse> update(@PathVariable Long id, @RequestBody GroceryListRequest request) {
        return ApiResponse.success(groceryListService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        groceryListService.deleteById(id);
    }
}
