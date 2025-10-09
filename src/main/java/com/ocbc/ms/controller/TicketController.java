package com.ocbc.ms.controller;

import com.ocbc.ms.dto.TicketRequest;
import com.ocbc.ms.entity.Ticket;
import com.ocbc.ms.service.TicketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@Tag(name = "Ticket Management", description = "APIs for managing tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping
    @Operation(summary = "Create a new ticket")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ticket created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketRequest request) {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ticketService.createTicket(request, username));
    }

    @GetMapping("/my-tickets")
    @Operation(summary = "Get current user's tickets")
    public ResponseEntity<List<Ticket>> getMyTickets() {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ticketService.getUserTickets(username));
    }

    @GetMapping("/pending")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all pending tickets (Admin only)")
    public ResponseEntity<List<Ticket>> getPendingTickets() {
        return ResponseEntity.ok(ticketService.getPendingTickets());
    }

    @PutMapping("/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Approve a ticket (Admin only)")
    public ResponseEntity<Ticket> approveTicket(@PathVariable Long id) {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ticketService.approveTicket(id, username));
    }

    @PutMapping("/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reject a ticket (Admin only)")
    public ResponseEntity<Ticket> rejectTicket(
            @PathVariable Long id,
            @RequestParam(required = false) String comments) {
        String username = getCurrentUsername();
        return ResponseEntity.ok(ticketService.rejectTicket(id, username, comments));
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
