package com.sumpositive.ticketing.ticket.service;

import java.util.List;

import com.sumpositive.ticketing.ticket.dto.TicketRequest;
import com.sumpositive.ticketing.ticket.dto.TicketResponse;
import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;
import com.sumpositive.ticketing.user.entity.User;

public interface TicketServiceIMPL {

	public TicketResponse createTicket(TicketRequest request);
	public List<TicketResponse> getAllTickets();
	public TicketResponse getTicketById(Long id);
	public TicketResponse updateTicket(Long id, TicketRequest request);
	public void deleteTicket(Long id);
	public List<TicketResponse> getTicketByStatus(TicketStatus status);
	public List<TicketResponse> getTicketByPriority(TicketPriority priority);
	public List<TicketResponse> getTicketByAssignee(Long assigneeId);
	public List<TicketResponse> getTicketsByStatusAndPriority(TicketStatus status, TicketPriority priority);
	public List<TicketResponse> getTicketsByAssigneeAndStatus(Long assigneeId, TicketStatus status);
	public TicketResponse updateTicketStatus(Long id, TicketStatus status);
	public TicketResponse assignTicket(Long ticketId, Long assigneeId);
	public TicketResponse unassignTicket(Long ticketId);
	public Ticket findTicketById(Long id);
	public User findActiveUserById(Long userId);
	
	
	
}
