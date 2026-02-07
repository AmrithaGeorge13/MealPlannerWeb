package com.healthyme.MealPlanner.household.model;

import com.healthyme.MealPlanner.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "households")
@Getter
@Setter
public class Household extends BaseEntity {

    @Column(nullable = false)
    private String name;
}
