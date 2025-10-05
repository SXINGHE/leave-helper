package com.ocbc.ms.service;

import com.ocbc.ms.dto.TicketRequest;
import com.ocbc.ms.entity.Ticket;
import com.ocbc.ms.exception.ResourceNotFoundException;
import com.ocbc.ms.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    private Ticket testTicket;
    private TicketRequest testRequest;

    @BeforeEach
    void setUp() {
        testTicket = new Ticket();
        testTicket.setId(1L);
        testTicket.setTitle("Test Ticket");
        testTicket.setDescription("Test Description");
        testTicket.setStatus("PENDING");
        testTicket.setCreatedBy("testuser");
        testTicket.setCreatedAt(LocalDateTime.now());

        testRequest = new TicketRequest();
        testRequest.setTitle("Test Ticket");
        testRequest.setDescription("Test Description");
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() {
        // Arrange
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        // Act
        Ticket result = ticketService.createTicket(testRequest, "testuser");

        // Assert
        assertNotNull(result);
        assertEquals("Test Ticket", result.getTitle());
        assertEquals("PENDING", result.getStatus());
        assertEquals("testuser", result.getCreatedBy());
        assertNotNull(result.getCreatedAt());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void getUserTickets_ShouldReturnUserTickets() {
        // Arrange
        List<Ticket> expectedTickets = Arrays.asList(testTicket);
        when(ticketRepository.findByCreatedBy("testuser")).thenReturn(expectedTickets);

        // Act
        List<Ticket> result = ticketService.getUserTickets("testuser");

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Ticket", result.get(0).getTitle());
        verify(ticketRepository, times(1)).findByCreatedBy("testuser");
    }

    @Test
    void getPendingTickets_ShouldReturnPendingTickets() {
        // Arrange
        List<Ticket> expectedTickets = Arrays.asList(testTicket);
        when(ticketRepository.findByStatus("PENDING")).thenReturn(expectedTickets);

        // Act
        List<Ticket> result = ticketService.getPendingTickets();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("PENDING", result.get(0).getStatus());
        verify(ticketRepository, times(1)).findByStatus("PENDING");
    }

    @Test
    void approveTicket_WhenTicketExists_ShouldApproveTicket() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);

        // Act
        Ticket result = ticketService.approveTicket(1L, "adminuser");

        // Assert
        assertNotNull(result);
        assertEquals("APPROVED", result.getStatus());
        assertEquals("adminuser", result.getApprovedBy());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void approveTicket_WhenTicketNotExists_ShouldThrowException() {
        // Arrange
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.approveTicket(999L, "adminuser");
        });
        verify(ticketRepository, times(1)).findById(999L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }

    @Test
    void rejectTicket_WhenTicketExists_ShouldRejectTicket() {
        // Arrange
        when(ticketRepository.findById(1L)).thenReturn(Optional.of(testTicket));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(testTicket);
        String rejectionReason = "Insufficient information";

        // Act
        Ticket result = ticketService.rejectTicket(1L, "adminuser", rejectionReason);

        // Assert
        assertNotNull(result);
        assertEquals("REJECTED", result.getStatus());
        assertEquals("adminuser", result.getApprovedBy());
        assertEquals(rejectionReason, result.getComments());
        verify(ticketRepository, times(1)).findById(1L);
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void rejectTicket_WhenTicketNotExists_ShouldThrowException() {
        // Arrange
        when(ticketRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            ticketService.rejectTicket(999L, "adminuser", "Test rejection");
        });
        verify(ticketRepository, times(1)).findById(999L);
        verify(ticketRepository, never()).save(any(Ticket.class));
    }
}
