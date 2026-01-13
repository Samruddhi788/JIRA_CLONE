package com.example.demo.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDate dueDate;
  @Enumerated(EnumType.STRING)
@Column(name = "status", nullable = false)
private Status status;


    @ManyToOne
    @JoinColumn(name="project_id", nullable=false)
    @JsonBackReference("project-tasks")
    private Project project; // Association with Project
    @ManyToOne
    @JoinColumn(name="user_id", nullable=true)
    @JsonBackReference("user-tasks")
    private User user; // Association with User
    
    public Task() {
    }
    public Task(String title,String description, LocalDate dueDate,  Status status, Project project) {
        this.description = description;
        this.dueDate = dueDate;
        this.project = project;
        this.status = status;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void  assignUser(User user){
        this.user=user;
    }

    public User getAssignedUser() {
        return this.user;
    }
   
}
