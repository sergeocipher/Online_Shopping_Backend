package com.Avishkar.InventoryManagementSystem.service;

import com.Avishkar.InventoryManagementSystem.dto.UserDto;
import com.Avishkar.InventoryManagementSystem.dto.request.UserCreateRequest;
import com.Avishkar.InventoryManagementSystem.dto.request.UserUpdateRequest;
import com.Avishkar.InventoryManagementSystem.entity.Role;
import com.Avishkar.InventoryManagementSystem.entity.Store;
import com.Avishkar.InventoryManagementSystem.entity.User;
import com.Avishkar.InventoryManagementSystem.mapper.UserMapper;
import com.Avishkar.InventoryManagementSystem.repository.RoleRepository;
import com.Avishkar.InventoryManagementSystem.repository.StoreRepository;
import com.Avishkar.InventoryManagementSystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final StoreRepository storeRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       StoreRepository storeRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.storeRepository = storeRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        // read-only to keep persistence context open for mapper access
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDto getById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public UserDto create(UserCreateRequest request) {
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));
        Store store = request.getStoreId() == null ? null : storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found"));

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setIsActive(request.getIsActive());
        user.setRole(role);
        user.setStore(store);
        return userMapper.toDto(userRepository.save(user));
    }

    @Transactional
    public UserDto update(Long id, UserUpdateRequest request) {
        User existing = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));
        Store store = request.getStoreId() == null ? null : storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Store not found"));

        existing.setUsername(request.getUsername());
        existing.setPassword(passwordEncoder.encode(request.getPassword()));
        existing.setIsActive(request.getIsActive());
        existing.setRole(role);
        existing.setStore(store);
        return userMapper.toDto(userRepository.save(existing));
    }

    @Transactional
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepository.deleteById(id);
    }
}
