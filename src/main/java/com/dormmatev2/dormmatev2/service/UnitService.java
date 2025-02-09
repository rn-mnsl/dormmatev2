package com.dormmatev2.dormmatev2.service;

// Import necessary classes and interface

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.repositories.UnitRepository;

// This indicates that this is a service 
@Service
public class UnitService {

    // this injects the UnitRepository to this class. 

    @Autowired
    private UnitRepository unitRepository;

    // method to save or create a unit into the database. 
     public Unit saveUnit(Unit unit) {

        // save the unit the database using the save method in the repository 
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

    // update unit when the admin uses it. 
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

        // save the unit to the database
        return unitRepository.save(existingUnit);
    }
}