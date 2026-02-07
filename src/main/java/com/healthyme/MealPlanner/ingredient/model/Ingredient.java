package com.healthyme.MealPlanner.ingredient.model;

import com.healthyme.MealPlanner.common.model.BaseEntity;
import com.healthyme.MealPlanner.enums.Unit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ingredients")
@Getter
@Setter
public class Ingredient extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Unit defaultUnit;
}
