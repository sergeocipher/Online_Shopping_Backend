package com.Avishkar.InventoryManagementSystem.security;

import com.Avishkar.InventoryManagementSystem.entity.User;
import com.Avishkar.InventoryManagementSystem.repository.UserRepository;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (Boolean.FALSE.equals(user.getIsActive())) {
            throw new DisabledException("User inactive");
        }

        String roleName = user.getRole() != null ? user.getRole().getName() : "USER";

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + roleName)))
                .accountLocked(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .disabled(Boolean.FALSE.equals(user.getIsActive()))
                .build();
    }
}
