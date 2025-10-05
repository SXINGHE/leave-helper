package com.ocbc.ms.service;

import com.ocbc.ms.dto.TicketRequest;
import com.ocbc.ms.entity.Ticket;
import com.ocbc.ms.exception.ResourceNotFoundException;
import com.ocbc.ms.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(TicketRequest request, String username) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCreatedBy(username);
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getUserTickets(String username) {
        return ticketRepository.findByCreatedBy(username);
    }

    public List<Ticket> getPendingTickets() {
        return ticketRepository.findByStatus("PENDING");
    }

    public Ticket approveTicket(Long id, String username) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id.toString()));

        ticket.setStatus("APPROVED");
        ticket.setApprovedBy(username);
        return ticketRepository.save(ticket);
    }

    public Ticket rejectTicket(Long id, String username, String comments) {
        Ticket ticket = ticketRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id.toString()));

        ticket.setStatus("REJECTED");
        ticket.setApprovedBy(username);
        ticket.setComments(comments);
        return ticketRepository.save(ticket);
    }
}
