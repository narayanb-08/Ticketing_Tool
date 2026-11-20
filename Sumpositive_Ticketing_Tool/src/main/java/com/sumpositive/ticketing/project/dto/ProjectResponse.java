package com.sumpositive.ticketing.project.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.sumpositive.ticketing.project.entity.ProjectStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectResponse {
    private Long id;
    private String projectKey;
    private String projectName;
    private String description;
    private ProjectStatus status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long projectManagerId;
    private String projectManagerName;
    private Long teamId;
    private String teamName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
