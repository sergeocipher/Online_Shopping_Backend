package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.RoleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.RoleDto;
import com.Avishkar.InventoryManagementSystem.dto.RoleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public List<RoleDto> getAll() {
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleDto getById(@PathVariable Long id) {
        return roleService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoleDto create(@Valid @RequestBody RoleCreateRequest request) {
        return roleService.create(request);
    }

    @PutMapping("/{id}")
    public RoleDto update(@PathVariable Long id, @Valid @RequestBody RoleUpdateRequest request) {
        return roleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        roleService.delete(id);
    }
}
