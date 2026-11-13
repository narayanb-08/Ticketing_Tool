package com.sumpositive.ticketing.ticket.mapper;

import org.springframework.stereotype.Component;
import com.sumpositive.ticketing.ticket.dto.TicketRequest;
import com.sumpositive.ticketing.ticket.dto.TicketResponse;
import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.user.entity.User;

@Component
public class TicketMapper {

	 // Convert Ticket entity into TicketResponse DTO
    public TicketResponse toResponse(Ticket ticket) {

        Long assigneeId = null;
        String assigneeName = null;

        if (ticket.getAssignee() != null) {
            assigneeId = ticket.getAssignee().getId();
            assigneeName = ticket.getAssignee().getFullName();
        }

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                assigneeId,
                assigneeName,
                ticket.getDueDate(),
                ticket.getCreatedAt(),
                ticket.getUpdatedAt()
        );
    }

    // Convert TicketRequest DTO into Ticket entity
    public Ticket toEntity(TicketRequest request) {

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(request.getStatus());
        ticket.setPriority(request.getPriority());
        ticket.setDueDate(request.getDueDate());

        return ticket;
    }

    // Update existing Ticket entity using request data
    public void updateEntity(Ticket ticket, TicketRequest request) {

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setStatus(request.getStatus());
        ticket.setPriority(request.getPriority());
        ticket.setDueDate(request.getDueDate());
    }

    // Assign a user to a ticket
    public void assignUser(Ticket ticket, User user) {
        ticket.setAssignee(user);
    }

    // Remove the assigned user from a ticket
    public void unassignUser(Ticket ticket) {
        ticket.setAssignee(null);
    }
}
