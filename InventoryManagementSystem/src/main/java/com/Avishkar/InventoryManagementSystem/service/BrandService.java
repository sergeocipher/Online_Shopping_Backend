package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.BrandCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.BrandDto;
import com.Avishkar.InventoryManagementSystem.dto.BrandUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Brand;
import com.Avishkar.InventoryManagementSystem.mapper.BrandMapper;
import com.Avishkar.InventoryManagementSystem.repository.BrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandService(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    public List<BrandDto> getAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::toDto)
                .collect(Collectors.toList());
    }

    public BrandDto getById(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
    }

    @Transactional
    public BrandDto create(BrandCreateRequest request) {
        Brand brand = brandMapper.toEntity(request);
        return brandMapper.toDto(brandRepository.save(brand));
    }

    @Transactional
    public BrandDto update(Long id, BrandUpdateRequest request) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
        brandMapper.updateEntity(existing, request);
        return brandMapper.toDto(brandRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }
        brandRepository.deleteById(id);
    }
}
