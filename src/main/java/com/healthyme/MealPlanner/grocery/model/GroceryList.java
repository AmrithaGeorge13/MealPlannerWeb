package com.healthyme.MealPlanner.grocery.model;

import com.healthyme.MealPlanner.common.model.BaseEntity;
import com.healthyme.MealPlanner.household.model.Household;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "grocery_lists")
@Getter
@Setter
public class GroceryList extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id", nullable = false)
    private Household household;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "groceryList", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroceryItem> items = new ArrayList<>();
}
