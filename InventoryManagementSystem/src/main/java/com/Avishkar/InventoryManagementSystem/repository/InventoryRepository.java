package com.Avishkar.InventoryManagementSystem.repository;

import com.Avishkar.InventoryManagementSystem.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
