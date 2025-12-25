package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
@PostMapping("/users")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<ApiResponse<Void>> createUser(
        @RequestBody CreateUserRequest request) {

    if (userRepository.findUserByEmail(request.email()).isPresent()) {
        throw new RuntimeException("User already exists");
    }

    User user = new User();
    user.setName(request.name());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setIsActive(true);
    user.setRoles(request.roles());

    userRepository.save(user);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("Admin created user successfully", null));
}
}