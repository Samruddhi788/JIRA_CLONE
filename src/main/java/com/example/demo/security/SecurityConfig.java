package com.example.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    // Custom JWT filter that we created in previous steps
    private final JwtAuthenticationFilter jwtAuthFilter;

    // Authentication provider (uses UserDetailsService + PasswordEncoder)
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            // STEP 1: Disable CSRF because JWT is stateless (no session)
            .csrf(csrf -> csrf.disable())

            // STEP 2: Define public & protected endpoints
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // login/register allowed
                .anyRequest().authenticated()            // everything else needs JWT
            )

            // STEP 3: Make session stateless (JWT replaces session)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // STEP 4: Tell Spring how authentication should work
            .authenticationProvider(authenticationProvider)

            // STEP 5: Add JWT filter BEFORE default auth filter
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
