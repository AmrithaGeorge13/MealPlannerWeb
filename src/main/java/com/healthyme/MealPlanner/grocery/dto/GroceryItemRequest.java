package com.healthyme.MealPlanner.grocery.dto;

import com.healthyme.MealPlanner.enums.GroceryItemSource;
import com.healthyme.MealPlanner.enums.Unit;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class GroceryItemRequest {

    private String name;
    private BigDecimal quantity;
    private Unit unit;
    private boolean checked;
    private GroceryItemSource source;
}
