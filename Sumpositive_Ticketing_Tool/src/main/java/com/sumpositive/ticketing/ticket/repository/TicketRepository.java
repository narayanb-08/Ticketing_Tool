package com.sumpositive.ticketing.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sumpositive.ticketing.ticket.entity.Ticket;
import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>{

	List<Ticket> findByStatus(TicketStatus status);

    List<Ticket> findByPriority(TicketPriority priority);

    List<Ticket> findByAssigneeId(Long assigneeId);

    List<Ticket> findByStatusAndPriority(TicketStatus status,TicketPriority priority );

    List<Ticket> findByAssigneeIdAndStatus( Long assigneeId,TicketStatus status);

    long countByStatus(TicketStatus status);

    long countByPriority(TicketPriority priority);
    
    List<Ticket> findByProjectId(Long projectId);
    
    
}
