package com.sumpositive.ticketing.ticket.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sumpositive.ticketing.common.exception.ResourceNotFoundException;
import com.sumpositive.ticketing.project.entity.Project;
import com.sumpositive.ticketing.project.repository.ProjectRepository;
import com.sumpositive.ticketing.ticket.dto.TicketRequest;
import com.sumpositive.ticketing.ticket.dto.TicketResponse;
import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;
import com.sumpositive.ticketing.ticket.mapper.TicketMapper;
import com.sumpositive.ticketing.ticket.repository.TicketRepository;
import com.sumpositive.ticketing.user.entity.User;
import com.sumpositive.ticketing.user.repository.UserRepository;

@Service
@Transactional
public class TicketService {

	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;
	private final TicketMapper ticketMapper;
	private final ProjectRepository projectRepository;

	public TicketService(TicketRepository ticketRepository, UserRepository userRepository, TicketMapper ticketMapper,
			ProjectRepository projectRepository) {

		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
		this.ticketMapper = ticketMapper;
		this.projectRepository = projectRepository;
	}

	public TicketResponse createTicket(TicketRequest request) {
		Ticket ticket = ticketMapper.toEntity(request);
		ticket.setCreatedBy(getAuthenticatedUser());

		if (request.getAssigneeId() != null) {
			User user = findActiveUserById(request.getAssigneeId());
			ticketMapper.assignUser(ticket, user);
		}
		
		if (request.getProjectId() != null) {

		    Project project = projectRepository.findById(request.getProjectId())
		            .orElseThrow(() ->
		                    new ResourceNotFoundException("Project not found"));

		    ticket.setProject(project);
		}

		Ticket savedTicket = ticketRepository.save(ticket);
		return ticketMapper.toResponse(savedTicket);
	}

	private User getAuthenticatedUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated()) {
			throw new AccessDeniedException("An authenticated user is required to create a ticket");
		}

		return userRepository.findByEmail(authentication.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Authenticated user was not found"));
	}

	public List<TicketResponse> getAllTickets() {
		return ticketRepository.findAll().stream().map(ticketMapper::toResponse).collect(Collectors.toList());
	}

	public TicketResponse getTicketById(Long id) {
		Ticket ticket = findTicketById(id);
		return ticketMapper.toResponse(ticket);
	}

	public TicketResponse updateTicket(Long id, TicketRequest request) {
		Ticket ticket = findTicketById(id);
		ticketMapper.updateEntity(ticket, request);

		if (request.getAssigneeId() != null) {
			User user = findActiveUserById(request.getAssigneeId());
			ticketMapper.assignUser(ticket, user);
		} else {
			ticketMapper.unassignUser(ticket);
		}
		
		if (request.getProjectId() != null) {

		    Project project = projectRepository.findById(request.getProjectId())
		            .orElseThrow(() ->
		                    new ResourceNotFoundException("Project not found"));

		    ticket.setProject(project);
		}

		Ticket updatedTicket = ticketRepository.save(ticket);
		return ticketMapper.toResponse(updatedTicket);
	}

	public void deleteTicket(Long id) {
		Ticket ticket = findTicketById(id);
		ticketRepository.delete(ticket);
	}

	public List<TicketResponse> getTicketsByStatus(TicketStatus status) {

		return ticketRepository.findByStatus(status).stream().map(ticketMapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<TicketResponse> getTicketsByPriority(TicketPriority priority) {

		return ticketRepository.findByPriority(priority).stream().map(ticketMapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<TicketResponse> getTicketsByAssignee(Long assigneeId) {

		return ticketRepository.findByAssigneeId(assigneeId).stream().map(ticketMapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<TicketResponse> getTicketsByStatusAndPriority(TicketStatus status, TicketPriority priority) {

		return ticketRepository.findByStatusAndPriority(status, priority).stream().map(ticketMapper::toResponse)
				.collect(Collectors.toList());
	}

	public List<TicketResponse> getTicketsByAssigneeAndStatus(Long assigneeId, TicketStatus status) {

		return ticketRepository.findByAssigneeIdAndStatus(assigneeId, status).stream().map(ticketMapper::toResponse)
				.collect(Collectors.toList());
	}

	public TicketResponse updateTicketStatus(Long id, TicketStatus status) {

		if (status == null) {
			throw new IllegalArgumentException("Ticket status is required");
		}

		Ticket ticket = findTicketById(id);
		ticket.setStatus(status);
		Ticket updatedTicket = ticketRepository.save(ticket);

		return ticketMapper.toResponse(updatedTicket);
	}

	public TicketResponse assignTicket(Long ticketId, Long assigneeId) {
		Ticket ticket = findTicketById(ticketId);
		User user = findActiveUserById(assigneeId);

		ticketMapper.assignUser(ticket, user);
		Ticket updatedTicket = ticketRepository.save(ticket);
		return ticketMapper.toResponse(updatedTicket);
	}

	public TicketResponse unassignTicket(Long ticketId) {
		Ticket ticket = findTicketById(ticketId);

		ticketMapper.unassignUser(ticket);
		Ticket updatedTicket = ticketRepository.save(ticket);
		return ticketMapper.toResponse(updatedTicket);
	}

	private Ticket findTicketById(Long id) {
		return ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Ticket not found with ID: " + id));
	}

	private User findActiveUserById(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

		if (!user.isActive()) {
			throw new IllegalArgumentException("Cannot assign a ticket to an inactive user");
		}
		return user;
	}
	
	public List<TicketResponse> getTicketsByProject(Long projectId) {

	    return ticketRepository.findByProjectId(projectId)
	            .stream()
	            .map(ticketMapper::toResponse)
	            .collect(Collectors.toList());
	}
}
