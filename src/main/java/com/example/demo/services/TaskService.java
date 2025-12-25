package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Project;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    public void saveTask(Long projectId, Task task) {
        Project project = projectService.getProjectById(projectId);

        project.getTasks().add(task);
        task.setProject(project);

        taskRepository.save(task);
    }

    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    public Task getByTaskId(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId));
    }

    public void deleteTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId));

        Project project = task.getProject();

        if (project == null) {
            throw new ResourceNotFoundException(
                    "Project not found for the task with id: " + taskId);
        }

        project.getTasks().remove(task);
        taskRepository.deleteById(taskId);
    }

    public void updateTaskById(Long id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + id));

        if (taskDetails.getTitle() != null &&
            taskDetails.getTitle().length() > 0 &&
            !task.getTitle().equals(taskDetails.getTitle())) {

            task.setTitle(taskDetails.getTitle());
        }

        if (taskDetails.getDescription() != null &&
            taskDetails.getDescription().length() > 0 &&
            !task.getDescription().equals(taskDetails.getDescription())) {

            task.setDescription(taskDetails.getDescription());
        }

        if (taskDetails.getStatus() != null &&
            !task.getStatus().equals(taskDetails.getStatus())) {

            task.setStatus(taskDetails.getStatus());
        }

        taskRepository.save(task);
    }

    public void assignTaskToUser(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Task not found with id: " + taskId));

        User user = userService.getUsrById(userId);
        task.assignUser(user);

        taskRepository.save(task);
    }
}
