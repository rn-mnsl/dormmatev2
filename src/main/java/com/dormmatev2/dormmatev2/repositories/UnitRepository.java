package com.dormmatev2.dormmatev2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dormmatev2.dormmatev2.model.Unit;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}