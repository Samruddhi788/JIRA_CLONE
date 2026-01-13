package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // <-- import added
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.Status;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import com.example.demo.services.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    public TaskService taskService;
    public TaskRepository taskRepository;

    @PreAuthorize("hasAuthority('TASK_CREATE')")
    @PostMapping("/create/{projectId}")
    public ResponseEntity<ApiResponse<Void>> createTask(
            @RequestBody Task task,
            @PathVariable Long projectId) {

        taskService.saveTask(projectId, task);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created successfully", null));
    }

    @PreAuthorize("hasAuthority('TASK_VIEW')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks() {
        return ResponseEntity.ok(
            ApiResponse.success("Tasks fetched successfully", taskService.getAll())
        );
    }

    @PreAuthorize("hasAuthority('TASK_VIEW')")
    @GetMapping("/byId/{taskId}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(@PathVariable Long taskId) {
        return ResponseEntity.ok(
            ApiResponse.success("Task fetched successfully", taskService.getByTaskId(taskId))
        );
    }

    @PreAuthorize("hasAuthority('TASK_DELETE')")
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTaskById(taskId);
        return ResponseEntity.ok(
            ApiResponse.success("Task deleted successfully", null)
        );
    }

    @PreAuthorize("hasAuthority('TASK_UPDATE')")
    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Void>> updateTask(
            @PathVariable Long id,
            @RequestBody Task taskDetails) {

        taskService.updateTaskById(id, taskDetails);
        return ResponseEntity.ok(
            ApiResponse.success("Task updated successfully", null)
        );
    }

    @PreAuthorize("hasAuthority('TASK_ASSIGN')")
    @GetMapping("/assign/{taskId}/{userId}")
    public ResponseEntity<ApiResponse<Void>> assignTaskToUser(
            @PathVariable Long taskId,
            @PathVariable Long userId) {

        taskService.assignTaskToUser(taskId, userId);
        return ResponseEntity.ok(
            ApiResponse.success("Task assigned successfully", null)
        );
    }
     
@PatchMapping("/{id}/status")
@PreAuthorize("hasAuthority('TASK_UPDATE')")
public ResponseEntity<?> changeTaskStatus(
        @PathVariable Long id,
        @RequestParam Status status
) {
    taskService.changeTaskStatus(id, status);
    return ResponseEntity.ok("Status updated");
}

}

