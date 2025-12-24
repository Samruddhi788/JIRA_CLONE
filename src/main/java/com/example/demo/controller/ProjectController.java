package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // <-- import added
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.services.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    @Autowired
    public ProjectService projectService;

    public User user;
    public Project project;

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @PostMapping("/create/{userId}")
    public void createProject(@RequestBody Project project, @PathVariable Long userId){
        this.project = project;
        projectService.saveProject(project, userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('DEVELOPER') or hasRole('TESTER')")
    @GetMapping("/getby/{id}") 
    public Project getProjectById(@PathVariable Long id){
        System.out.println("Fetching project with id: " + id);
        return projectService.getProjectById(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('DEVELOPER') or hasRole('TESTER')")
    @GetMapping("/all")
    public List<Project> getAllProjects(){
        List<Project> projects = projectService.getAll();
        return projects;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('DEVELOPER') or hasRole('TESTER')")
    @GetMapping("/{userId}")
    public Project getProjectByUserId(@PathVariable Long userId){
        return projectService.getProjectByUserId(userId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @PutMapping("/edit/{id}")
    public void updateProject(@PathVariable Long id, @RequestBody Project projectDetails){
        Project project = projectService.getProjectById(id);
        projectService.updateProject(id, projectDetails);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @DeleteMapping("/delete/{id}")
    public void deleteProject(@PathVariable Long id){
        projectService.projectRepository.deleteById(id);
        System.out.println("Project with id " + id + " deleted successfully.");
    }
}
