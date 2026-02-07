package com.healthyme.MealPlanner.user.model;

import com.healthyme.MealPlanner.common.model.BaseEntity;
import com.healthyme.MealPlanner.household.model.Household;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(unique = true)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "household_id")
    private Household household;
}
