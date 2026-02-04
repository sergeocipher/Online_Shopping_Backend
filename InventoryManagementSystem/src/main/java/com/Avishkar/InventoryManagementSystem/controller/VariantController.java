package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.VariantCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.VariantDto;
import com.Avishkar.InventoryManagementSystem.dto.VariantUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.VariantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variants")
public class VariantController {

    private final VariantService variantService;

    public VariantController(VariantService variantService) {
        this.variantService = variantService;
    }

    @GetMapping
    public List<VariantDto> getAll() {
        return variantService.getAll();
    }

    @GetMapping("/{id}")
    public VariantDto getById(@PathVariable Long id) {
        return variantService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VariantDto create(@Valid @RequestBody VariantCreateRequest request) {
        return variantService.create(request);
    }

    @PutMapping("/{id}")
    public VariantDto update(@PathVariable Long id, @Valid @RequestBody VariantUpdateRequest request) {
        return variantService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        variantService.delete(id);
    }
}
