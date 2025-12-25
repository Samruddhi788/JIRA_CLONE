package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // <-- import added
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Project;
import com.example.demo.services.ProjectService;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    public ProjectService projectService;
    

    @PreAuthorize("hasAuthority('PROJECT_CREATE')")
    @PostMapping("/create/{userId}")
    public ResponseEntity<ApiResponse<Void>> createProject(
            @RequestBody Project project,
            @PathVariable Long userId) {

        projectService.saveProject(project, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Project created successfully", null));
    }

    @PreAuthorize("hasAuthority('PROJECT_VIEW')")
    @GetMapping("/getby/{id}")
    public ResponseEntity<ApiResponse<Project>> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(
            ApiResponse.success("Project fetched successfully", projectService.getProjectById(id))
        );
    }

    @PreAuthorize("hasAuthority('PROJECT_VIEW')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Project>>> getAllProjects() {
        return ResponseEntity.ok(
            ApiResponse.success("Projects fetched successfully", projectService.getAll())
        );
    }

    @PreAuthorize("hasAuthority('PROJECT_VIEW')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Project>> getProjectByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(
            ApiResponse.success("Project fetched successfully", projectService.getProjectByUserId(userId))
        );
    }

    @PreAuthorize("hasAuthority('PROJECT_UPDATE')")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Void>> updateProject(
            @PathVariable Long id,
            @RequestBody Project projectDetails) {

        projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(
            ApiResponse.success("Project updated successfully", null)
        );
    }

    @PreAuthorize("hasAuthority('PROJECT_DELETE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable Long id) {
        projectService.projectRepository.deleteById(id);
        return ResponseEntity.ok(
            ApiResponse.success("Project deleted successfully", null)
        );
    }
}
