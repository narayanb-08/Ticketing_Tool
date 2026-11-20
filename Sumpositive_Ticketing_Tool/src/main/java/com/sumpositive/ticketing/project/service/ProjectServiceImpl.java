package com.sumpositive.ticketing.project.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.ticketing.common.exception.DuplicateResourceException;
import com.sumpositive.ticketing.common.exception.ResourceNotFoundException;
import com.sumpositive.ticketing.project.dto.CreateProjectRequest;
import com.sumpositive.ticketing.project.dto.ProjectResponse;
import com.sumpositive.ticketing.project.dto.UpdateProjectRequest;
import com.sumpositive.ticketing.project.entity.Project;
import com.sumpositive.ticketing.project.entity.ProjectStatus;
import com.sumpositive.ticketing.project.repository.ProjectRepository;
import com.sumpositive.ticketing.team.entity.Team;
import com.sumpositive.ticketing.team.repository.TeamRepository;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.enums.AccountStatus;
import com.sumpositive.ticketing.user.enums.UserRole;
import com.sumpositive.ticketing.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Override
    public ProjectResponse createProject(CreateProjectRequest request) {
        String key = request.getProjectKey().trim().toUpperCase(Locale.ROOT);
        if (projectRepository.existsByProjectKey(key)) {
            throw new DuplicateResourceException("Project key already exists: " + key);
        }
        validateDates(request.getStartDate(), request.getEndDate());

        Project project = new Project();
        project.setProjectKey(key);
        project.setProjectName(request.getProjectName().trim());
        project.setDescription(request.getDescription());
        project.setStatus(parseStatus(request.getStatus(), ProjectStatus.PLANNED));
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectManager(getProjectManager(request.getProjectManagerId()));
        project.setTeam(getTeam(request.getTeamId()));
        return toResponse(projectRepository.save(project));
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long projectId) {
        return toResponse(getProject(projectId));
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectResponse getProjectByKey(String projectKey) {
        Project project = projectRepository.findByProjectKey(projectKey.toUpperCase(Locale.ROOT))
                .orElseThrow(() -> new ResourceNotFoundException("Project not found: " + projectKey));
        return toResponse(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    @Override
    public ProjectResponse updateProject(Long projectId, UpdateProjectRequest request) {
        Project project = getProject(projectId);
        validateDates(request.getStartDate(), request.getEndDate());
        project.setProjectName(request.getProjectName().trim());
        project.setDescription(request.getDescription());
        project.setStatus(parseStatus(request.getStatus(), null));
        project.setStartDate(request.getStartDate());
        project.setEndDate(request.getEndDate());
        project.setProjectManager(getProjectManager(request.getProjectManagerId()));
        project.setTeam(getTeam(request.getTeamId()));
        return toResponse(projectRepository.save(project));
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepository.delete(getProject(projectId));
    }

    private Project getProject(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + id));
    }

    private User getProjectManager(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Project manager not found: " + userId));
        if (!user.isActive() || user.getAccountStatus() != AccountStatus.APPROVED) {
            throw new IllegalArgumentException("Project manager must have an active, approved account");
        }
        if (user.getUserRole() != UserRole.PROJECT_MANAGER && user.getUserRole() != UserRole.ADMIN) {
            throw new IllegalArgumentException("Selected user must have PROJECT_MANAGER or ADMIN role");
        }
        return user;
    }

    private Team getTeam(Long teamId) {
        if (teamId == null) return null;
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with ID: " + teamId));
    }

    private ProjectStatus parseStatus(String value, ProjectStatus defaultValue) {
        if (value == null || value.isBlank()) {
            if (defaultValue != null) return defaultValue;
            throw new IllegalArgumentException("Project status is required");
        }
        try {
            return ProjectStatus.valueOf(value.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid project status. Allowed values: PLANNED, ACTIVE, ON_HOLD, COMPLETED, ARCHIVED");
        }
    }

    private void validateDates(java.time.LocalDate start, java.time.LocalDate end) {
        if (start != null && end != null && end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }
    }

    private ProjectResponse toResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setProjectKey(project.getProjectKey());
        response.setProjectName(project.getProjectName());
        response.setDescription(project.getDescription());
        response.setStatus(project.getStatus());
        response.setStartDate(project.getStartDate());
        response.setEndDate(project.getEndDate());
        response.setProjectManagerId(project.getProjectManager().getId());
        response.setProjectManagerName(project.getProjectManager().getFullName());
        if (project.getTeam() != null) {
            response.setTeamId(project.getTeam().getId());
            response.setTeamName(project.getTeam().getTeamName());
        }
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        return response;
    }
}
