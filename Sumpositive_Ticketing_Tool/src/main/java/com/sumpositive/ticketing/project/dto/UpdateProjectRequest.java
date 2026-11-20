package com.sumpositive.ticketing.project.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProjectRequest {
    @NotBlank(message = "Project name is required")
    @Size(max = 150, message = "Project name cannot exceed 150 characters")
    private String projectName;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    @NotNull(message = "Project status is required")
    private String status;

    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull(message = "Project manager ID is required")
    private Long projectManagerId;

    private Long teamId;
}
