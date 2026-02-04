package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.RoleCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.RoleDto;
import com.Avishkar.InventoryManagementSystem.dto.RoleUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Role;
import com.Avishkar.InventoryManagementSystem.mapper.RoleMapper;
import com.Avishkar.InventoryManagementSystem.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    public List<RoleDto> getAll() {
        return roleRepository.findAll()
                .stream()
                .map(roleMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoleDto getById(Long id) {
        return roleRepository.findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
    }

    @Transactional
    public RoleDto create(RoleCreateRequest request) {
        Role role = roleMapper.toEntity(request);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Transactional
    public RoleDto update(Long id, RoleUpdateRequest request) {
        Role existing = roleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        roleMapper.updateEntity(existing, request);
        return roleMapper.toDto(roleRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found");
        }
        roleRepository.deleteById(id);
    }
}
