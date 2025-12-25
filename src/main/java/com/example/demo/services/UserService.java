package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public void softDeleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        user.setIsActive(false);
        userRepository.save(user);
    }

    public User getUsrById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));
    }

    public User getUsrByEMail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException(
                    "User not found with email: " + email);
        }
        return user.get();
    }

    public void modifyUser(Long id, String name, String email) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        if (name != null && name.length() > 0 && !user.getName().equals(name)) {
            user.setName(name);
        }

        if (email != null && email.length() > 0 && !user.getEmail().equals(email)) {
            user.setEmail(email);
        }

        userRepository.save(user);
    }
}
