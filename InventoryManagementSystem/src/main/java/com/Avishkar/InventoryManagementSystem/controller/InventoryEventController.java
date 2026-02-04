package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.InventoryEventCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryEventDto;
import com.Avishkar.InventoryManagementSystem.service.InventoryEventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory-events")
public class InventoryEventController {

    private final InventoryEventService inventoryEventService;

    public InventoryEventController(InventoryEventService inventoryEventService) {
        this.inventoryEventService = inventoryEventService;
    }

    @GetMapping
    public List<InventoryEventDto> getAll() {
        return inventoryEventService.getAll();
    }

    @GetMapping("/{id}")
    public InventoryEventDto getById(@PathVariable Long id) {
        return inventoryEventService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryEventDto create(@Valid @RequestBody InventoryEventCreateRequest request) {
        return inventoryEventService.create(request);
    }
}
