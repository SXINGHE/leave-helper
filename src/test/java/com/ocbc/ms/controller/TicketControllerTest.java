package com.ocbc.ms.controller;

import com.ocbc.ms.dto.TicketRequest;
import com.ocbc.ms.entity.Ticket;
import com.ocbc.ms.service.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class TicketControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TicketService ticketService;

    @InjectMocks
    private TicketController ticketController;

    private Ticket testTicket;
    private TicketRequest testRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
        
        // Setup test data
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

        // Setup security context
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "testuser", 
            null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() throws Exception {
        when(ticketService.createTicket(any(TicketRequest.class), anyString())).thenReturn(testTicket);

        mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Test Ticket\",\"description\":\"Test Description\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Ticket"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(ticketService, times(1)).createTicket(any(TicketRequest.class), eq("testuser"));
    }

    @Test
    void getMyTickets_ShouldReturnUserTickets() throws Exception {
        when(ticketService.getUserTickets("testuser")).thenReturn(Collections.singletonList(testTicket));

        mockMvc.perform(get("/api/tickets/my-tickets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Ticket"))
                .andExpect(jsonPath("$[0].createdBy").value("testuser"));

        verify(ticketService, times(1)).getUserTickets("testuser");
    }

    @Test
    void getPendingTickets_WhenAdmin_ShouldReturnPendingTickets() throws Exception {
        // Setup admin user
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "admin", 
            null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(ticketService.getPendingTickets()).thenReturn(Collections.singletonList(testTicket));

        mockMvc.perform(get("/api/tickets/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("PENDING"));

        verify(ticketService, times(1)).getPendingTickets();
    }

    @Test
    void approveTicket_WhenAdmin_ShouldApproveTicket() throws Exception {
        // Setup admin user
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "admin", 
            null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        testTicket.setStatus("APPROVED");
        testTicket.setApprovedBy("admin");
        when(ticketService.approveTicket(anyLong(), anyString())).thenReturn(testTicket);

        mockMvc.perform(put("/api/tickets/1/approve"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("APPROVED"))
                .andExpect(jsonPath("$.approvedBy").value("admin"));

        verify(ticketService, times(1)).approveTicket(1L, "admin");
    }

    @Test
    void rejectTicket_WhenAdmin_ShouldRejectTicket() throws Exception {
        // Setup admin user
        Authentication auth = new UsernamePasswordAuthenticationToken(
            "admin", 
            null, 
            Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        testTicket.setStatus("REJECTED");
        testTicket.setApprovedBy("admin");
        testTicket.setComments("Insufficient information");
        when(ticketService.rejectTicket(anyLong(), anyString(), anyString())).thenReturn(testTicket);

        mockMvc.perform(put("/api/tickets/1/reject?comments=Insufficient%20information"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("REJECTED"))
                .andExpect(jsonPath("$.comments").value("Insufficient information"));

        verify(ticketService, times(1)).rejectTicket(1L, "admin", "Insufficient information");
    }
}
