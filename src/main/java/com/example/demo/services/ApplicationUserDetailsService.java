

package com.example.demo.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.debug("🔍 loadUserByUsername called with email: {}", username);

        return userRepository.findUserByEmail(username)
            .orElseThrow(() -> {
                log.error("❌ User NOT FOUND for email: {}", username);
                return new UsernameNotFoundException(
                    "User not found with email: " + username
                );
            });
    }
}
