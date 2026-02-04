package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.StyleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StyleDto;
import com.Avishkar.InventoryManagementSystem.dto.StyleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Brand;
import com.Avishkar.InventoryManagementSystem.entity.Category;
import com.Avishkar.InventoryManagementSystem.entity.Style;
import com.Avishkar.InventoryManagementSystem.mapper.StyleMapper;
import com.Avishkar.InventoryManagementSystem.repository.BrandRepository;
import com.Avishkar.InventoryManagementSystem.repository.CategoryRepository;
import com.Avishkar.InventoryManagementSystem.repository.StyleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StyleService {

    private final StyleRepository styleRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final StyleMapper styleMapper;

    public StyleService(StyleRepository styleRepository,
                        BrandRepository brandRepository,
                        CategoryRepository categoryRepository,
                        StyleMapper styleMapper) {
        this.styleRepository = styleRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.styleMapper = styleMapper;
    }

    public List<StyleDto> getAll() {
        return styleRepository.findAll()
                .stream()
                .map(styleMapper::toDto)
                .collect(Collectors.toList());
    }

    public StyleDto getById(Long id) {
        return styleRepository.findById(id)
                .map(styleMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Style not found"));
    }

    @Transactional
    public StyleDto create(StyleCreateRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        Style style = styleMapper.toEntity(request);
        style.setBrand(brand);
        style.setCategory(category);
        return styleMapper.toDto(styleRepository.save(style));
    }

    @Transactional
    public StyleDto update(Long id, StyleUpdateRequest request) {
        Style existing = styleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Style not found"));
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        styleMapper.updateEntity(existing, request);
        existing.setBrand(brand);
        existing.setCategory(category);
        return styleMapper.toDto(styleRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!styleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Style not found");
        }
        styleRepository.deleteById(id);
    }
}
