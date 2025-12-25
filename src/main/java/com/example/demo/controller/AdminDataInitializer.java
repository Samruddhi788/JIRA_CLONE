package com.example.demo.controller;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Authority;
import com.example.demo.model.RoleAuthority;
import com.example.demo.model.Roles;
import com.example.demo.model.User;
import com.example.demo.repository.RoleAuthorityRepository;
import com.example.demo.repository.UserRepository;

@Configuration
public class AdminDataInitializer {
 RoleAuthorityRepository roleAuthorityRepository;

    @Bean
CommandLineRunner initAdmin(UserRepository userRepository,
                            PasswordEncoder passwordEncoder,
                            RoleAuthorityRepository roleAuthorityRepository) {
    return args -> {

        // ===== 1️⃣ CREATE ADMIN IF NOT EXISTS =====
        

            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail("admin123@example.com");
            admin.setPassword(passwordEncoder.encode("admin123456"));
            admin.setIsActive(true);
            admin.setRoles(List.of(Roles.ADMIN));

            userRepository.save(admin);

            System.out.println("✅ Default ADMIN created");
        



        // ===== 2️⃣ SEED ROLE → AUTHORITY MAPPING =====
        if (roleAuthorityRepository.count() == 0) {

            roleAuthorityRepository.saveAll(List.of(

                // ===== ADMIN =====
                new RoleAuthority(null, Authority.TASK_VIEW, Roles.ADMIN),
                new RoleAuthority(null, Authority.TASK_CREATE, Roles.ADMIN),
                new RoleAuthority(null, Authority.TASK_ASSIGN, Roles.ADMIN),
                new RoleAuthority(null, Authority.TASK_UPDATE, Roles.ADMIN),
                new RoleAuthority(null, Authority.TASK_COMMENT, Roles.ADMIN),
                new RoleAuthority(null, Authority.TASK_DELETE, Roles.ADMIN),
                new RoleAuthority(null, Authority.PROJECT_CREATE, Roles.ADMIN),
                new RoleAuthority(null, Authority.PROJECT_UPDATE, Roles.ADMIN),
                new RoleAuthority(null, Authority.PROJECT_VIEW, Roles.ADMIN),
                new RoleAuthority(null, Authority.PROJECT_DELETE, Roles.ADMIN),
                
                // ===== PROJECT MANAGER =====
                new RoleAuthority(null, Authority.TASK_VIEW, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.TASK_CREATE, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.TASK_ASSIGN, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.TASK_UPDATE, Roles.PROJECT_MANAGER),
                 new RoleAuthority(null, Authority.TASK_DELETE, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.PROJECT_CREATE, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.PROJECT_UPDATE, Roles.PROJECT_MANAGER),
                new RoleAuthority(null, Authority.PROJECT_VIEW, Roles.PROJECT_MANAGER),
               
                // ===== DEVELOPER =====
                new RoleAuthority(null, Authority.TASK_VIEW, Roles.DEVELOPER),
                new RoleAuthority(null, Authority.TASK_UPDATE, Roles.DEVELOPER),

                // ===== TESTER =====
                new RoleAuthority(null, Authority.TASK_VIEW, Roles.TESTER),
                new RoleAuthority(null, Authority.TASK_COMMENT, Roles.TESTER),

                // ===== USER =====
                new RoleAuthority(null, Authority.TASK_VIEW, Roles.USER),
                new RoleAuthority(null, Authority.PROJECT_VIEW, Roles.USER),
                new RoleAuthority(null, Authority.USER_MANAGE, Roles.ADMIN)
            ));

            System.out.println("✅ Role–Authority mapping initialized");
        }
    };
}
}
