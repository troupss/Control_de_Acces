package com.crni99.qrcodegenerator.repository;

import com.crni99.qrcodegenerator.models.Tickets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketsRepository extends JpaRepository<Tickets, String> {
    Tickets findByTicketId(String ticketId);
}
