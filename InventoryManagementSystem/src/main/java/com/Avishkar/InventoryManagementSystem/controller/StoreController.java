package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.StoreCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StoreDto;
import com.Avishkar.InventoryManagementSystem.dto.StoreUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public List<StoreDto> getAll() {
        return storeService.getAll();
    }

    @GetMapping("/{id}")
    public StoreDto getById(@PathVariable Long id) {
        return storeService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StoreDto create(@Valid @RequestBody StoreCreateRequest request) {
        return storeService.create(request);
    }

    @PutMapping("/{id}")
    public StoreDto update(@PathVariable Long id, @Valid @RequestBody StoreUpdateRequest request) {
        return storeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        storeService.delete(id);
    }
}
