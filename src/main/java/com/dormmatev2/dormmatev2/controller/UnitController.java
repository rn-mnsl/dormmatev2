package com.dormmatev2.dormmatev2.controller;

import com.dormmatev2.dormmatev2.model.Unit;
import com.dormmatev2.dormmatev2.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @PostMapping
    public ResponseEntity<Unit> createUnit(@RequestBody Unit unit) {
         Unit savedUnit = unitService.saveUnit(unit);
        return new ResponseEntity<>(savedUnit, HttpStatus.CREATED);
   }

    @GetMapping
    public ResponseEntity<List<Unit>> getAllUnits() {
        List<Unit> units = unitService.findAllUnits();
        return new ResponseEntity<>(units, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Unit> getUnitById(@PathVariable Long id) {
        Optional<Unit> unit = unitService.findUnitById(id);
        return unit.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
              .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
     @PutMapping("/{id}")
     public ResponseEntity<Unit> updateUnit(@PathVariable Long id, @RequestBody Unit unit) {
         try {
          Unit updatedUnit = unitService.updateUnit(id, unit);
            return new ResponseEntity<>(updatedUnit, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
       }
     }


    @DeleteMapping("/{id}")
   public ResponseEntity<HttpStatus> deleteUnit(@PathVariable Long id) {
        try {
            unitService.deleteUnit(id);
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }
}