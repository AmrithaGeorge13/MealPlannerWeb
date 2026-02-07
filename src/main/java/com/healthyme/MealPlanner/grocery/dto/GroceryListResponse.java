package com.healthyme.MealPlanner.grocery.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GroceryListResponse {

    private Long id;
    private Long householdId;
    private String name;
    private List<GroceryItemResponse> items;
}
