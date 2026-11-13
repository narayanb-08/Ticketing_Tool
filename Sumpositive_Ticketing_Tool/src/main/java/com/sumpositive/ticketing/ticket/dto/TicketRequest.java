package com.sumpositive.ticketing.ticket.dto;

import java.time.LocalDate;

import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class TicketRequest {

	@NotBlank(message = "Ticket title is required")
    @Size(min = 3, max = 150,  message = "Title must be between 3 and 150 characters")
    private String title;

    @Size(max = 5000, message = "Description cannot exceed 5000 characters")
    private String description;

    @NotNull(message = "Ticket priority is required")
    private TicketPriority priority = TicketPriority.MEDIUM;

    private TicketStatus status = TicketStatus.TO_DO;

    private Long assigneeId;

    @FutureOrPresent(message = "Due date cannot be in the past")
    private LocalDate dueDate;

	public TicketRequest() {
		
	}

	public TicketRequest(String title, String description,TicketPriority priority, TicketStatus status,Long assigneeId, LocalDate dueDate) {
		this.title = title;
		this.description = description;
		this.priority = priority;
		this.status = status;
		this.assigneeId = assigneeId;
		this.dueDate = dueDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TicketPriority getPriority() {
		return priority;
	}

	public void setPriority(TicketPriority priority) {
		this.priority = priority;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}  
}
