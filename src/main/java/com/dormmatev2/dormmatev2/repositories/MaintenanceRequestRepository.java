package com.dormmatev2.dormmatev2.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dormmatev2.dormmatev2.model.MaintenanceRequest;


@Repository
public interface MaintenanceRequestRepository extends JpaRepository<MaintenanceRequest, Long> {
  List<MaintenanceRequest> findByTenant_UserId(Long tenantId);
}