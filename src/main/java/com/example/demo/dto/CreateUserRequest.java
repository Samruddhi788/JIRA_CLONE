package com.example.demo.dto;

import java.util.List;

import com.example.demo.model.Roles;

    public record CreateUserRequest(
    String name,
    String email,
    String password,
    List<Roles> roles
) {}


