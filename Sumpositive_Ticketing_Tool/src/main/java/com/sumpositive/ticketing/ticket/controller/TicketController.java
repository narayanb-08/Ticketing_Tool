
package com.sumpositive.ticketing.ticket.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import com.sumpositive.ticketing.ticket.dto.TicketRequest;
import com.sumpositive.ticketing.ticket.dto.TicketResponse;
import com.sumpositive.ticketing.ticket.dto.TicketStatusUpdateRequest;
import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;
import com.sumpositive.ticketing.ticket.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

	private final TicketService ticketService;

	public TicketController(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	@PostMapping("/createTicket")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest request) {
		TicketResponse response = ticketService.createTicket(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/getAllTickets")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getAllTickets() {
		return ResponseEntity.ok(ticketService.getAllTickets());
	}

	@GetMapping("/getTicketById/{id}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
		return ResponseEntity.ok(ticketService.getTicketById(id));
	}

	@PutMapping("/updateTicketById/{id}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id, @RequestBody TicketRequest request) {
		return ResponseEntity.ok(ticketService.updateTicket(id, request));
	}

	@DeleteMapping("/deleteTicketById/{id}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
		ticketService.deleteTicket(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/getTicketByStatus/{status}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getTicketsByStatus(@PathVariable TicketStatus status) {
		return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
	}

	@GetMapping("/getTicketByPriority/{priority}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getTicketsByPriority(@PathVariable TicketPriority priority) {
		return ResponseEntity.ok(ticketService.getTicketsByPriority(priority));
	}

	@GetMapping("/getTicketByAssignee/{assigneeId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getTicketsByAssignee(@PathVariable Long assigneeId) {
		return ResponseEntity.ok(ticketService.getTicketsByAssignee(assigneeId));
	}

	@GetMapping("/filter")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> filterTickets(@RequestParam TicketStatus status,
			@RequestParam TicketPriority priority) {
		return ResponseEntity.ok(ticketService.getTicketsByStatusAndPriority(status, priority));
	}

	@GetMapping("/assignee/{assigneeId}/status/{status}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getTicketsByAssigneeAndStatus(@PathVariable Long assigneeId,
			@PathVariable TicketStatus status) {
		return ResponseEntity.ok(ticketService.getTicketsByAssigneeAndStatus(assigneeId, status));
	}

	@PatchMapping("/updateStatus/{id}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> updateTicketStatus(@PathVariable Long id,
			@RequestBody TicketStatusUpdateRequest request) {
		return ResponseEntity.ok(ticketService.updateTicketStatus(id, request.getStatus()));
	}

	@PatchMapping("/{id}/assign/{assigneeId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> assignTicket(@PathVariable Long id, @PathVariable Long assigneeId) {
		return ResponseEntity.ok(ticketService.assignTicket(id, assigneeId));
	}

	@PatchMapping("/unassign/{id}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<TicketResponse> unassignTicket(@PathVariable Long id) {
		return ResponseEntity.ok(ticketService.unassignTicket(id));
	}

	@GetMapping("/project/{projectId}")
	@PreAuthorize("@accountStatusSecurity.isApproved()")
	public ResponseEntity<List<TicketResponse>> getTicketsByProject(@PathVariable Long projectId) {
		return ResponseEntity.ok(ticketService.getTicketsByProject(projectId));
	}
}