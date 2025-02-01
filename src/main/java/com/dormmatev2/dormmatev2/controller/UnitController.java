package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/units") // Base URL for all endpoints in this controller
public class UnitController {

    @Autowired // Injects the UnitService dependency
    private UnitService unitService;

    @PostMapping // Handles POST requests to create a new unit
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
         Unit savedUnit = unitService.saveUnit(unit); // Saves the unit to the database
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED); // Returns the saved unit with HTTP 201 status
   }

    @GetMapping // Handles GET requests to retrieve all units
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.findAllUnits(); // Retrieves all units from the database
        return new ResponseEntity<>(units, HttpStatus.OK); // Returns the list of units with HTTP 200 status
    }

    @GetMapping("/{id}") // Handles GET requests to retrieve a unit by ID
    public ResponseEntity<Unit> getUnitById(@PathVariable Long id) {
        Optional<Unit> unit = unitService.findUnitById(id); // Retrieves the unit by ID
        return unit.map(value -> new ResponseEntity<>(value, HttpStatus.OK)) // Returns the unit if found
              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Returns HTTP 404 if unit not found
    }

     @PutMapping("/{id}") // Handles PUT requests to update a unit by ID
     public ResponseEntity<Unit> updateUnit(@PathVariable Long id, @RequestBody Unit unit) {
         try {
          Unit updatedUnit = unitService.updateUnit(id, unit); // Updates the unit in the database
            return new ResponseEntity<>(updatedUnit, HttpStatus.OK); // Returns the updated unit with HTTP 200 status
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Returns HTTP 404 if unit not found
       }
     }

    @DeleteMapping("/{id}") // Handles DELETE requests to delete a unit by ID
   public ResponseEntity<HttpStatus> deleteUnit(@PathVariable Long id) {
        try {
            unitService.deleteUnit(id); // Deletes the unit from the database
          return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Returns HTTP 204 if deletion is successful
       } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Returns HTTP 500 if an error occurs
       }
    }
}