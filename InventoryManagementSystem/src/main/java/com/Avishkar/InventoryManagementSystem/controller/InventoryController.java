package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.InventoryCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryDto;
import com.Avishkar.InventoryManagementSystem.dto.InventoryUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<InventoryDto> getAll() {
        return inventoryService.getAll();
    }

    @GetMapping("/{id}")
    public InventoryDto getById(@PathVariable Long id) {
        return inventoryService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryDto create(@Valid @RequestBody InventoryCreateRequest request) {
        return inventoryService.create(request);
    }

    @PutMapping("/{id}")
    public InventoryDto update(@PathVariable Long id, @Valid @RequestBody InventoryUpdateRequest request) {
        return inventoryService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        inventoryService.delete(id);
    }
}
