package com.example.demo.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner; // Association with User
    
    @OneToMany(mappedBy="project",cascade=CascadeType.ALL,orphanRemoval=true)
    @JsonManagedReference("project-tasks")
    List<Task> tasks= new ArrayList<>(); // Association with Task
        public Project() {
    }
    public Project( String name, String description,LocalDate startDate,LocalDate endDate//,User owner
    ) {
    
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
       // this.owner = owner;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public User getOwner() {
        return owner;
    }
    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
  // In Project.java
public void addTask(Task task) {
    if (tasks == null) {
        tasks = new ArrayList<>();
    }
    tasks.add(task);        // Add to project list
    task.setProject(this);  // Set the owning side
}

public void removeTask(Task task) {
    if (tasks != null) {
        tasks.remove(task);   // Remove from project list
        task.setProject(null); // Clear owning side
    }
}

}

