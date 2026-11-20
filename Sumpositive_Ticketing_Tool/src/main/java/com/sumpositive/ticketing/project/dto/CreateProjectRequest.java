package com.sumpositive.ticketing.project.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProjectRequest {
    @NotBlank(message = "Project key is required")
    @Size(min = 2, max = 10, message = "Project key must contain 2 to 10 characters")
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9]*$", message = "Project key must start with a letter and contain only letters or numbers")
    private String projectKey;

    @NotBlank(message = "Project name is required")
    @Size(max = 150, message = "Project name cannot exceed 150 characters")
    private String projectName;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private String status;
    private LocalDate startDate;
    private LocalDate endDate;

    @NotNull(message = "Project manager ID is required")
    private Long projectManagerId;

    private Long teamId;
}
