package com.example.demo.security;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;    
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.RoleAuthority;
import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.RoleAuthorityRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleAuthorityRepository roleAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

          Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        for (Roles role : user.getRoles()) {

            // ROLE_ authorities (optional but good practice)
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

            // Fetch authorities mapped to this role
            List<RoleAuthority> roleAuthorities =
                    roleAuthorityRepository.findByRole(role);

            for (RoleAuthority ra : roleAuthorities) {
                authorities.add(
                        new SimpleGrantedAuthority(ra.getAuthority().name())
                );
            }
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(authorities)
                .disabled(!user.isActive())
                .build();
    }
    }
