package com.dormmatev2.dormmatev2.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

     public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    public List<Unit> findAllUnits() {
        return unitRepository.findAll();
    }

    public Optional<Unit> findUnitById(Long id) {
        return unitRepository.findById(id);
    }

    public void deleteUnit(Long id) {
       unitRepository.deleteById(id);
    }
      public Unit updateUnit(Long id, Unit unitDetails) {
        Unit existingUnit = unitRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unit not found with id: " + id));

        // Update fields only if they are provided in the request
        if (unitDetails.getUnitName() != null && !unitDetails.getUnitName().isEmpty()) {
            existingUnit.setUnitName(unitDetails.getUnitName());
        }

        if (unitDetails.getDescription() != null && !unitDetails.getDescription().isEmpty()) {
            existingUnit.setDescription(unitDetails.getDescription());
        }

        return unitRepository.save(existingUnit);
    }
    // Add other methods as needed for your application
}