package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.InventoryCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryDto;
import com.Avishkar.InventoryManagementSystem.dto.InventoryUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Inventory;
import com.Avishkar.InventoryManagementSystem.entity.Store;
import com.Avishkar.InventoryManagementSystem.entity.Variant;
import com.Avishkar.InventoryManagementSystem.mapper.InventoryMapper;
import com.Avishkar.InventoryManagementSystem.repository.InventoryRepository;
import com.Avishkar.InventoryManagementSystem.repository.StoreRepository;
import com.Avishkar.InventoryManagementSystem.repository.VariantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final StoreRepository storeRepository;
    private final VariantRepository variantRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryService(InventoryRepository inventoryRepository,
                            StoreRepository storeRepository,
                            VariantRepository variantRepository,
                            InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.storeRepository = storeRepository;
        this.variantRepository = variantRepository;
        this.inventoryMapper = inventoryMapper;
    }

    public List<InventoryDto> getAll() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventoryMapper::toDto)
                .collect(Collectors.toList());
    }

    public InventoryDto getById(Long id) {
        return inventoryRepository.findById(id)
                .map(inventoryMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));
    }

    @Transactional
    public InventoryDto create(InventoryCreateRequest request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found"));
        Variant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Variant not found"));
        Inventory inventory = inventoryMapper.toEntity(request);
        inventory.setStore(store);
        inventory.setVariant(variant);
        return inventoryMapper.toDto(inventoryRepository.save(inventory));
    }

    @Transactional
    public InventoryDto update(Long id, InventoryUpdateRequest request) {
        Inventory existing = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found"));
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found"));
        Variant variant = variantRepository.findById(request.getVariantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Variant not found"));
        inventoryMapper.updateEntity(existing, request);
        existing.setStore(store);
        existing.setVariant(variant);
        return inventoryMapper.toDto(inventoryRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory not found");
        }
        inventoryRepository.deleteById(id);
    }
}
