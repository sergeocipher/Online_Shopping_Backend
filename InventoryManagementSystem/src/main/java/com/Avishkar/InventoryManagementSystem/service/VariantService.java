package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.VariantCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.VariantDto;
import com.Avishkar.InventoryManagementSystem.dto.VariantUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Style;
import com.Avishkar.InventoryManagementSystem.entity.Variant;
import com.Avishkar.InventoryManagementSystem.mapper.VariantMapper;
import com.Avishkar.InventoryManagementSystem.repository.StyleRepository;
import com.Avishkar.InventoryManagementSystem.repository.VariantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantService {

    private final VariantRepository variantRepository;
    private final StyleRepository styleRepository;
    private final VariantMapper variantMapper;

    public VariantService(VariantRepository variantRepository,
                          StyleRepository styleRepository,
                          VariantMapper variantMapper) {
        this.variantRepository = variantRepository;
        this.styleRepository = styleRepository;
        this.variantMapper = variantMapper;
    }

    public List<VariantDto> getAll() {
        return variantRepository.findAll()
                .stream()
                .map(variantMapper::toDto)
                .collect(Collectors.toList());
    }

    public VariantDto getById(Long id) {
        return variantRepository.findById(id)
                .map(variantMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
    }

    @Transactional
    public VariantDto create(VariantCreateRequest request) {
        Style style = styleRepository.findById(request.getStyleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Style not found"));
        Variant variant = variantMapper.toEntity(request);
        variant.setStyle(style);
        return variantMapper.toDto(variantRepository.save(variant));
    }

    @Transactional
    public VariantDto update(Long id, VariantUpdateRequest request) {
        Variant existing = variantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found"));
        Style style = styleRepository.findById(request.getStyleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Style not found"));
        variantMapper.updateEntity(existing, request);
        existing.setStyle(style);
        return variantMapper.toDto(variantRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!variantRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Variant not found");
        }
        variantRepository.deleteById(id);
    }
}
