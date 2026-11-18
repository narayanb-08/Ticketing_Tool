
package com.sumpositive.ticketing.task.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.sumpositive.ticketing.task.dto.TaskRequest;
import com.sumpositive.ticketing.task.dto.TaskResponse;
import com.sumpositive.ticketing.task.entity.TaskStatus;
import com.sumpositive.ticketing.task.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @Valid @RequestBody TaskRequest request) {

        return new ResponseEntity<>(
                taskService.createTask(request),
                HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    public ResponseEntity<TaskResponse> getTaskById(
            @PathVariable Long taskId) {

        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @GetMapping
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    public ResponseEntity<List<TaskResponse>> getAllTasks() {

        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/my-tasks")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER') or hasRole('DEVELOPER') or hasRole('TESTER') or hasRole('EMPLOYEE') or hasRole('SUPPORT_ENGINEER'))")
    public ResponseEntity<List<TaskResponse>> getMyTasks() {

        return ResponseEntity.ok(taskService.getMyTasks());
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    public ResponseEntity<List<TaskResponse>> getTasksByStatus(
            @PathVariable TaskStatus status) {

        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    @PutMapping("/{taskId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequest request) {

        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    @PatchMapping("/{taskId}/status")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER') or hasRole('TEAM_LEADER'))")
    public ResponseEntity<TaskResponse> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {

        return ResponseEntity.ok(
                taskService.updateTaskStatus(taskId, status));
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('ADMIN') or hasRole('PROJECT_MANAGER'))")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {

        taskService.deleteTask(taskId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/my-status")
    @PreAuthorize("@accountStatusSecurity.isApproved() and (hasRole('DEVELOPER') or hasRole('TESTER') or hasRole('EMPLOYEE') or hasRole('SUPPORT_ENGINEER'))")
    public ResponseEntity<TaskResponse> updateMyTaskStatus(
            @PathVariable Long taskId,
            @RequestParam TaskStatus status) {

        return ResponseEntity.ok(
                taskService.updateMyTaskStatus(taskId, status));
    }
}
