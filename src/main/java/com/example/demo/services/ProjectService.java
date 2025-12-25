package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Project;
import com.example.demo.model.User;
import com.example.demo.repository.ProjectRepository;
import com.example.demo.repository.UserRepository;

@Service
public class ProjectService {

    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public void saveProject(Project project, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + userId));

        project.setOwner(user);
        projectRepository.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));
    }

    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Project getProjectByUserId(Long userId) {
        return projectRepository.findByOwnerId(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with user id: " + userId));
    }

    public Project updateProject(Long id, Project projectDetails) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));

        if (projectDetails.getName() != null &&
            !projectDetails.getName().equals(project.getName())) {

            boolean exists = projectRepository.existsByNameAndOwnerId(
                    projectDetails.getName(),
                    project.getOwner().getId());

            if (exists) {
                throw new BadRequestException(
                        "Project with this name already exists for the user");
            }

            project.setName(projectDetails.getName());
        }

        if (projectDetails.getDescription() != null) {
            project.setDescription(projectDetails.getDescription());
        }

        if (projectDetails.getStartDate() != null) {
            project.setStartDate(projectDetails.getStartDate());
        }

        if (projectDetails.getEndDate() != null) {
            project.setEndDate(projectDetails.getEndDate());
        }

        return projectRepository.save(project);
    }

    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Project not found with id: " + id));

        projectRepository.deleteById(id);
    }
}
