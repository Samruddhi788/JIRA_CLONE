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

import com.example.demo.model.Task;
import com.example.demo.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    public TaskService taskService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @PostMapping("/create/{projectId}")
    public void createTask(@RequestBody Task task, @PathVariable Long projectId){
        taskService.saveTask(projectId, task);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('DEVELOPER') or hasRole('TESTER')")
    @GetMapping("/all")
    public List<Task> getAllTasks(){
        return taskService.getAll();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('DEVELOPER') or hasRole('TESTER')")
    @GetMapping("/byId/{taskId}")
    public Task getTaskById(@PathVariable Long taskId){
        return taskService.getByTaskId(taskId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        taskService.deleteTaskById(taskId);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @PutMapping("/update/{id}")
    public void updateTask(@PathVariable Long id, @RequestBody Task taskDetails){
        taskService.updateTaskById(id, taskDetails);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('PROJECT_MANAGER')")
    @GetMapping("/assign/{taskId}/{userId}")
    public void assignTaskToUser(@PathVariable Long taskId, @PathVariable Long userId){
        taskService.assignTaskToUser(taskId, userId);
    }
}
