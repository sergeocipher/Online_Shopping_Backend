package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.BrandCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.BrandDto;
import com.Avishkar.InventoryManagementSystem.dto.BrandUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<BrandDto> getAll() {
        return brandService.getAll();
    }

    @GetMapping("/{id}")
    public BrandDto getById(@PathVariable Long id) {
        return brandService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDto create(@Valid @RequestBody BrandCreateRequest request) {
        return brandService.create(request);
    }

    @PutMapping("/{id}")
    public BrandDto update(@PathVariable Long id, @Valid @RequestBody BrandUpdateRequest request) {
        return brandService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}
