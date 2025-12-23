package com.example.demo.controller;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Configuration
public class AdminDataInitializer {

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {

            // Delete old admin if exists
            userRepository.findUserByEmail("admin@example.com");            

            // Create fresh admin with roles
            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setIsActive(true);
            admin.setRoles(List.of(Roles.ADMIN)); // ✅ Assign role properly

            userRepository.save(admin);

            System.out.println("✅ Default ADMIN created with ROLE_ADMIN");
        };
    }
}
