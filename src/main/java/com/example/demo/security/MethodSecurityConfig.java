package com.example.demo.security;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
@Configuration
@EnableMethodSecurity   // ✅ REQUIRED for @PreAuthorize
public class MethodSecurityConfig {
}
//so that we can use @PreAuthorize annotations in our service layer to secure methods based on roles or permissions.