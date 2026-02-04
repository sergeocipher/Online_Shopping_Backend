package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.StoreCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.StoreDto;
import com.Avishkar.InventoryManagementSystem.dto.StoreUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Store;
import com.Avishkar.InventoryManagementSystem.mapper.StoreMapper;
import com.Avishkar.InventoryManagementSystem.repository.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreService(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    public List<StoreDto> getAll() {
        return storeRepository.findAll()
                .stream()
                .map(storeMapper::toDto)
                .collect(Collectors.toList());
    }

    public StoreDto getById(Long id) {
        return storeRepository.findById(id)
                .map(storeMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
    }

    @Transactional
    public StoreDto create(StoreCreateRequest request) {
        Store store = storeMapper.toEntity(request);
        return storeMapper.toDto(storeRepository.save(store));
    }

    @Transactional
    public StoreDto update(Long id, StoreUpdateRequest request) {
        Store existing = storeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found"));
        storeMapper.updateEntity(existing, request);
        return storeMapper.toDto(storeRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!storeRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Store not found");
        }
        storeRepository.deleteById(id);
    }
}
