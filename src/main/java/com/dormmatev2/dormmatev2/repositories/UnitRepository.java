package com.dormmatev2.dormmatev2.repositories;

import com.dormmatev2.dormmatev2.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
}
