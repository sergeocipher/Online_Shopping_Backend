package com.Avishkar.InventoryManagementSystem.repository;

import com.Avishkar.InventoryManagementSystem.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
