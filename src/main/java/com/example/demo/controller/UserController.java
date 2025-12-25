package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.dto.ApiResponse;



@RestController
@PreAuthorize("hasAuthority('USER_MANAGE')")
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    //private ApiResponse<List<User>> ApiResponse;
   // private Response ApiResponse;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {
        return ResponseEntity.ok(
            ApiResponse.success("Users fetched successfully", userService.getAllUsers())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.success("User fetched successfully", userService.getUsrById(id))
        );
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Void>> createUser(@RequestBody User user) {
        userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("User created successfully", null));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id);
        return ResponseEntity.ok(
            ApiResponse.success("User deleted successfully", null)
        );
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<ApiResponse<Void>> updateDetails(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email) {

        userService.modifyUser(id, name, email);
        return ResponseEntity.ok(
            ApiResponse.success("User updated successfully", null)
        );
    }
}
