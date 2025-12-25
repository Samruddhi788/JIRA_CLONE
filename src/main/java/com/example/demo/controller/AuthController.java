
package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.JwtService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthenticationManager authenticationManager;
        private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //  @PostMapping("/register")
    // public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

    //     log.debug("➡️ Register attempt for email: {}", request.getEmail());

    //     if (userRepository.findUserByEmail(request.getEmail()).isPresent()) {
    //         log.warn("❌ Email already exists: {}", request.getEmail());
    //         return ResponseEntity.badRequest().body("Email already registered");
    //     }

    //     User user = new User();
    //     user.setName(request.getName());
    //     user.setEmail(request.getEmail());
    //     user.setPassword(passwordEncoder.encode(request.getPassword()));
    //     user.setIsActive(true);

    //     userRepository.save(user);

    //     log.debug("✅ User registered successfully: {}", request.getEmail());
    //     return ResponseEntity.ok("User registered successfully");
    //}

    @PostMapping("/login")
public ResponseEntity<ApiResponse<AuthResponse>> login(
        @RequestBody LoginRequest request) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
            )
        );

    String token = jwtService.generateToken(
        (UserDetails) authentication.getPrincipal()
    );

    return ResponseEntity.ok(
        ApiResponse.success("Login successful", new AuthResponse(token))
    );
}
}
