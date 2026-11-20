package com.sumpositive.ticketing.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumpositive.ticketing.project.entity.Project;
import com.sumpositive.ticketing.project.entity.ProjectStatus;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByProjectKey(String projectKey);
    Optional<Project> findByProjectKey(String projectKey);
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByProjectManagerId(Long managerId);
    List<Project> findByTeamId(Long teamId);
}
