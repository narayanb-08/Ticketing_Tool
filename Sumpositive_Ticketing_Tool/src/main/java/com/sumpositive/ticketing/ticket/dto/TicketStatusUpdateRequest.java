package com.sumpositive.ticketing.ticket.dto;

import com.sumpositive.ticketing.ticket.entity.TicketStatus;

import jakarta.validation.constraints.NotNull;

public class TicketStatusUpdateRequest {

	@NotNull(message = "Ticket status is required")
    private TicketStatus status;

	public TicketStatusUpdateRequest() {
	
	}

	public TicketStatusUpdateRequest(@NotNull(message = "Ticket status is required") TicketStatus status) {
		this.status = status;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}
	
	
}
