package com.Avishkar.InventoryManagementSystem.repository;

import com.Avishkar.InventoryManagementSystem.entity.InventoryEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryEventRepository extends JpaRepository<InventoryEvent, Long> {
}
