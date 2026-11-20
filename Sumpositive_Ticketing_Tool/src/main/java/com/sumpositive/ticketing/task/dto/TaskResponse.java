
package com.sumpositive.ticketing.task.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.sumpositive.ticketing.task.entity.TaskPriority;
import com.sumpositive.ticketing.task.entity.TaskStatus;

import lombok.Data;

@Data
public class TaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private LocalDate dueDate;

    private Long assignedToId;

    private String assignedToName;

    private Long createdById;

    private String createdByName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    
    private Long ticketId;
    
    private Long projectId;

    private String projectName;

    private String projectKey;
}
