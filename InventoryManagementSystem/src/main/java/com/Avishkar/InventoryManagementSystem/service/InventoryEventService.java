package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.InventoryEventCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.InventoryEventDto;
import com.Avishkar.InventoryManagementSystem.entity.Inventory;
import com.Avishkar.InventoryManagementSystem.entity.InventoryEvent;
import com.Avishkar.InventoryManagementSystem.mapper.InventoryEventMapper;
import com.Avishkar.InventoryManagementSystem.repository.InventoryEventRepository;
import com.Avishkar.InventoryManagementSystem.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryEventService {

    private final InventoryEventRepository inventoryEventRepository;
    private final InventoryRepository inventoryRepository;
    private final InventoryEventMapper inventoryEventMapper;

    public InventoryEventService(InventoryEventRepository inventoryEventRepository,
                                 InventoryRepository inventoryRepository,
                                 InventoryEventMapper inventoryEventMapper) {
        this.inventoryEventRepository = inventoryEventRepository;
        this.inventoryRepository = inventoryRepository;
        this.inventoryEventMapper = inventoryEventMapper;
    }

    public List<InventoryEventDto> getAll() {
        return inventoryEventRepository.findAll()
                .stream()
                .map(inventoryEventMapper::toDto)
                .collect(Collectors.toList());
    }

    public InventoryEventDto getById(Long id) {
        return inventoryEventRepository.findById(id)
                .map(inventoryEventMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory event not found"));
    }

    @Transactional
    public InventoryEventDto create(InventoryEventCreateRequest request) {
        Inventory inventory = inventoryRepository.findById(request.getInventoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Inventory not found"));

        InventoryEvent.EventType eventType;
        try {
            eventType = InventoryEvent.EventType.valueOf(request.getEventType().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid event type");
        }

        int newQuantity = inventory.getQuantity() + request.getQuantityChange();
        if (newQuantity < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Resulting quantity cannot be negative");
        }
        inventory.setQuantity(newQuantity);
        inventoryRepository.save(inventory);

        InventoryEvent event = inventoryEventMapper.toEntity(request);
        event.setInventory(inventory);
        event.setEventType(eventType);
        event.setQuantityChange(request.getQuantityChange());
        event.setReason(request.getReason());

        return inventoryEventMapper.toDto(inventoryEventRepository.save(event));
    }
}
