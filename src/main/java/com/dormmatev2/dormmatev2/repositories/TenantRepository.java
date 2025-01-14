package com.dormmatev2.dormmatev2.repositories;

import com.dormmatev2.dormmatev2.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByUser_Username(String username);
}
