package com.healthyme.MealPlanner.household.service;

import com.healthyme.MealPlanner.common.exception.ResourceNotFoundException;
import com.healthyme.MealPlanner.household.model.Household;
import com.healthyme.MealPlanner.household.repository.HouseholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HouseholdService {

    private final HouseholdRepository householdRepository;

    @Transactional(readOnly = true)
    public List<Household> findAll() {
        return householdRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Household findById(Long id) {
        return householdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Household", id));
    }

    @Transactional
    public Household create(Household household) {
        return householdRepository.save(household);
    }

    @Transactional
    public Household update(Long id, Household updates) {
        Household existing = findById(id);
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        return householdRepository.save(existing);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!householdRepository.existsById(id)) {
            throw new ResourceNotFoundException("Household", id);
        }
        householdRepository.deleteById(id);
    }
}
