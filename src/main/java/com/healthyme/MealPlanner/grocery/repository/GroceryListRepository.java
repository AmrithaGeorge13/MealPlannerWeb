package com.healthyme.MealPlanner.grocery.repository;

import com.healthyme.MealPlanner.grocery.model.GroceryList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {

    List<GroceryList> findByHouseholdId(Long householdId);
}
