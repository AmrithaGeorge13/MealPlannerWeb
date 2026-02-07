package com.healthyme.MealPlanner.grocery.service;

import com.healthyme.MealPlanner.common.exception.ResourceNotFoundException;
import com.healthyme.MealPlanner.grocery.dto.GroceryItemResponse;
import com.healthyme.MealPlanner.grocery.dto.GroceryListRequest;
import com.healthyme.MealPlanner.grocery.dto.GroceryListResponse;
import com.healthyme.MealPlanner.grocery.model.GroceryItem;
import com.healthyme.MealPlanner.grocery.model.GroceryList;
import com.healthyme.MealPlanner.grocery.repository.GroceryListRepository;
import com.healthyme.MealPlanner.household.repository.HouseholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroceryListService {

    private final GroceryListRepository groceryListRepository;
    private final HouseholdRepository householdRepository;

    @Transactional(readOnly = true)
    public List<GroceryListResponse> findAll() {
        return groceryListRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GroceryListResponse> findByHouseholdId(Long householdId) {
        return groceryListRepository.findByHouseholdId(householdId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GroceryListResponse findById(Long id) {
        return toResponse(getEntity(id));
    }

    @Transactional(readOnly = true)
    public GroceryList getEntity(Long id) {
        return groceryListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("GroceryList", id));
    }

    @Transactional
    public GroceryListResponse create(GroceryListRequest request) {
        GroceryList list = new GroceryList();
        list.setHousehold(householdRepository.findById(request.getHouseholdId())
                .orElseThrow(() -> new ResourceNotFoundException("Household", request.getHouseholdId())));
        list.setName(request.getName());
        return toResponse(groceryListRepository.save(list));
    }

    @Transactional
    public GroceryListResponse update(Long id, GroceryListRequest request) {
        GroceryList existing = getEntity(id);
        if (request.getHouseholdId() != null) {
            existing.setHousehold(householdRepository.findById(request.getHouseholdId())
                    .orElseThrow(() -> new ResourceNotFoundException("Household", request.getHouseholdId())));
        }
        if (request.getName() != null) existing.setName(request.getName());
        return toResponse(groceryListRepository.save(existing));
    }

    @Transactional
    public void deleteById(Long id) {
        if (!groceryListRepository.existsById(id)) {
            throw new ResourceNotFoundException("GroceryList", id);
        }
        groceryListRepository.deleteById(id);
    }

    private GroceryListResponse toResponse(GroceryList entity) {
        List<GroceryItemResponse> items = entity.getItems() == null
                ? new ArrayList<>()
                : entity.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());
        return GroceryListResponse.builder()
                .id(entity.getId())
                .householdId(entity.getHousehold().getId())
                .name(entity.getName())
                .items(items)
                .build();
    }

    private GroceryItemResponse toItemResponse(GroceryItem item) {
        return GroceryItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .quantity(item.getQuantity())
                .unit(item.getUnit())
                .checked(item.isChecked())
                .source(item.getSource())
                .build();
    }
}
