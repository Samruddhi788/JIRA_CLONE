package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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


@RestController

@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers(){

        List<User> UserList= userService.getAllUsers();
        return UserList;

    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id){
        User user=userService.getUsrById(id);   
        return user;
    }

    @PostMapping ("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public void createUser(@RequestBody User user){
       userService.createUser(user);
    }

    @DeleteMapping("/delete/{id}")
   // @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(@PathVariable Long id){
        System.out.println("Deleting user with id: " + id);
        userService.softDeleteUser(id);
    }
    
    @PutMapping("modify/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void updateDetails(@PathVariable Long id, @RequestParam(required=false) String name, @RequestParam(required=false) String email) {
         System.out.println("Received name=" + name + ", email=" + email);
       userService.modifyUser(id,name,email);
    }
   

}
