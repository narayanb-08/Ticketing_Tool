
package com.sumpositive.ticketing.dashboard.service;

import org.springframework.stereotype.Service;
import com.sumpositive.ticketing.dashboard.dto.DashboardResponse;
import com.sumpositive.ticketing.ticket.entity.TicketStatus;
import com.sumpositive.ticketing.ticket.entity.TicketPriority;
import com.sumpositive.ticketing.ticket.repository.TicketRepository;

@Service
public class DashboardService {

    private final TicketRepository ticketRepository;

    public DashboardService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public DashboardResponse getDashboardStatistics() {
        long totalTickets = ticketRepository.count();

       
        long todoTickets = ticketRepository.countByStatus(TicketStatus.TO_DO);
        long inProgressTickets =ticketRepository.countByStatus(TicketStatus.IN_PROGRESS);

        long doneTickets =ticketRepository.countByStatus(TicketStatus.DONE);

        
        long lowPriorityTickets =ticketRepository.countByPriority(TicketPriority.LOW);
        long mediumPriorityTickets = ticketRepository.countByPriority(TicketPriority.MEDIUM);
        long highPriorityTickets =ticketRepository.countByPriority(TicketPriority.HIGH);

 
        return new DashboardResponse(
                totalTickets,
                todoTickets,
                inProgressTickets,
                doneTickets,
                lowPriorityTickets,
                mediumPriorityTickets,
                highPriorityTickets
        );
    }
}
