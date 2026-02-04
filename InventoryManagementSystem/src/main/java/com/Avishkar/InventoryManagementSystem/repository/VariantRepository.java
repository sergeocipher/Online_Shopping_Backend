package com.Avishkar.InventoryManagementSystem.repository;

import com.Avishkar.InventoryManagementSystem.entity.Variant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariantRepository extends JpaRepository<Variant, Long> {
}
