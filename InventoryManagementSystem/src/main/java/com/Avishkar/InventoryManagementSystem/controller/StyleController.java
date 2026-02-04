package com.Avishkar.InventoryManagementSystem.controller;

import com.Avishkar.InventoryManagementSystem.dto.StyleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StyleDto;
import com.Avishkar.InventoryManagementSystem.dto.StyleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.service.StyleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/styles")
public class StyleController {

    private final StyleService styleService;

    public StyleController(StyleService styleService) {
        this.styleService = styleService;
    }

    @GetMapping
    public List<StyleDto> getAll() {
        return styleService.getAll();
    }

    @GetMapping("/{id}")
    public StyleDto getById(@PathVariable Long id) {
        return styleService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StyleDto create(@Valid @RequestBody StyleCreateRequest request) {
        return styleService.create(request);
    }

    @PutMapping("/{id}")
    public StyleDto update(@PathVariable Long id, @Valid @RequestBody StyleUpdateRequest request) {
        return styleService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        styleService.delete(id);
    }
}
