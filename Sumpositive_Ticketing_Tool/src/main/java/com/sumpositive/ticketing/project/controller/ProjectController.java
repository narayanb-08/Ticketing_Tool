package com.sumpositive.ticketing.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.sumpositive.ticketing.project.dto.CreateProjectRequest;
import com.sumpositive.ticketing.project.dto.ProjectResponse;
import com.sumpositive.ticketing.project.dto.UpdateProjectRequest;
import com.sumpositive.ticketing.project.service.ProjectService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(request));
    }

    @GetMapping
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<List<ProjectResponse>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

    @GetMapping("/key/{projectKey}")
    @PreAuthorize("@accountStatusSecurity.isApproved()")
    public ResponseEntity<ProjectResponse> getProjectByKey(@PathVariable String projectKey) {
        return ResponseEntity.ok(projectService.getProjectByKey(projectKey));
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasAnyRole('ADMIN', 'PROJECT_MANAGER')")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long projectId,
            @Valid @RequestBody UpdateProjectRequest request) {
        return ResponseEntity.ok(projectService.updateProject(projectId, request));
    }

    @DeleteMapping("/{projectId}")
    @PreAuthorize("@accountStatusSecurity.isApproved() and hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return ResponseEntity.noContent().build();
    }
}
