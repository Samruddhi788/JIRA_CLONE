package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    
@OneToMany(mappedBy="user")
@JsonManagedReference("user-tasks")
  List<Task> tasks;
    
    
   
 public List<Task> getTasks() { 
    return tasks;
    }
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public User() {
    }
 public User(String name, String email, String password) {
        this.email = email;
       // this.id = id;
        this.name = name;
        this.password = password;
        this.isActive = true;
    }

    public boolean isIsActive() {
       // isActive = true;
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = true;
    }
}
