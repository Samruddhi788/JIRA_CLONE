package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping     ("/tasks")  
public class TaskController {
    @Autowired
    public TaskService taskService;
    @PostMapping("/create/{projectId}")
    public void createTask(@RequestBody Task task,@PathVariable Long projectId){
        // Logic to create a task under a specific project
        taskService.saveTask(projectId,task );
    }
    @GetMapping("/all")
    public List<Task> getAllTasks(){
    
        return taskService.getAll();
        
    }
    @GetMapping("/byId/{taskId}")
    public Task getTaskById(@PathVariable Long taskId){
       Task task=taskService.getByTaskId(taskId);
        return task;
    }
    @DeleteMapping("/delete/{taskId}")
    public void deleteTask(@PathVariable Long taskId){
        // Logic to delete a task by its ID
    
     taskService.deleteTaskById(taskId);
     
    }
    @PutMapping("/update/{id}")
    public void updateTask(@PathVariable Long id,@RequestBody Task taskDetails){
        // Logic to update a task by its ID
        taskService.updateTaskById(id,taskDetails);
    }
 @GetMapping("/assign/{taskId}/{userId}")
    public void assignTaskToUser(@PathVariable Long taskId,@PathVariable Long userId){
     taskService.assignTaskToUser(taskId,userId);        
    }
}
