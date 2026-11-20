package com.sumpositive.ticketing.project.service;

import java.util.List;
import com.sumpositive.ticketing.project.dto.CreateProjectRequest;
import com.sumpositive.ticketing.project.dto.ProjectResponse;
import com.sumpositive.ticketing.project.dto.UpdateProjectRequest;

public interface ProjectService {
    ProjectResponse createProject(CreateProjectRequest request);
    ProjectResponse getProjectById(Long projectId);
    ProjectResponse getProjectByKey(String projectKey);
    List<ProjectResponse> getAllProjects();
    ProjectResponse updateProject(Long projectId, UpdateProjectRequest request);
    void deleteProject(Long projectId);
}
