package com.Avishkar.InventoryManagementSystem.repository;

import com.Avishkar.InventoryManagementSystem.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
}
