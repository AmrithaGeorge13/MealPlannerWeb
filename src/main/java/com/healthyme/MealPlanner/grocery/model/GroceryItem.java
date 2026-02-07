package com.healthyme.MealPlanner.grocery.model;

import com.healthyme.MealPlanner.enums.GroceryItemSource;
import com.healthyme.MealPlanner.enums.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "grocery_items")
@Getter
@Setter
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grocery_list_id", nullable = false)
    private GroceryList groceryList;

    @Column(nullable = false)
    private String name;

    private BigDecimal quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    private boolean checked;

    @Enumerated(EnumType.STRING)
    private GroceryItemSource source;
}
