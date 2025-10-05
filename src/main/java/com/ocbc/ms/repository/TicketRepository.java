package com.ocbc.ms.repository;

import com.ocbc.ms.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByCreatedBy(String createdBy);
    List<Ticket> findByStatus(String status);
}
