
package com.sumpositive.ticketing.ticket.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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

  
    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest request) {
        TicketResponse response = ticketService.createTicket(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    
    @GetMapping("/getTickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

   
    @GetMapping("/getTicketById/{id}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

    
    @PutMapping("/updateTicket/{id}")
    public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long id,@RequestBody TicketRequest request) {
        return ResponseEntity.ok(ticketService.updateTicket(id, request));
    }


    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }

    
    @GetMapping("/status/{status}")
    public ResponseEntity<List<TicketResponse>> getTicketsByStatus(@PathVariable TicketStatus status) {
        return ResponseEntity.ok(ticketService.getTicketsByStatus(status));
    }


    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<TicketResponse>> getTicketsByPriority(@PathVariable TicketPriority priority) {
        return ResponseEntity.ok(ticketService.getTicketsByPriority(priority));
    }

   
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByAssignee(@PathVariable Long assigneeId) {
        return ResponseEntity.ok(ticketService.getTicketsByAssignee(assigneeId));
    }

    
    @GetMapping("/filter")
    public ResponseEntity<List<TicketResponse>> filterTickets( @RequestParam TicketStatus status,@RequestParam TicketPriority priority) {
        return ResponseEntity.ok( ticketService.getTicketsByStatusAndPriority(status, priority));
    }

    
    @GetMapping("/assignee/{assigneeId}/status/{status}")
    public ResponseEntity<List<TicketResponse>>
            getTicketsByAssigneeAndStatus(@PathVariable Long assigneeId,@PathVariable TicketStatus status) {
        return ResponseEntity.ok( ticketService.getTicketsByAssigneeAndStatus(
                        assigneeId, status));
    }


    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<TicketResponse> updateTicketStatus(@PathVariable Long id,@RequestBody TicketStatusUpdateRequest request) {
        return ResponseEntity.ok( ticketService.updateTicketStatus( id, request.getStatus()));
    }

    
    @PatchMapping("/{id}/assign/{assigneeId}")
    public ResponseEntity<TicketResponse> assignTicket(@PathVariable Long id, @PathVariable Long assigneeId) {
        return ResponseEntity.ok(ticketService.assignTicket(id, assigneeId));
    }


    @PatchMapping("/unassign/{id}")
    public ResponseEntity<TicketResponse> unassignTicket(@PathVariable Long id) {
        return ResponseEntity.ok(ticketService.unassignTicket(id));
    }

}