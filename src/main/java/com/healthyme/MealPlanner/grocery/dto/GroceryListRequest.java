package com.healthyme.MealPlanner.grocery.dto;

import lombok.Data;

@Data
public class GroceryListRequest {

    private Long householdId;
    private String name;
}
