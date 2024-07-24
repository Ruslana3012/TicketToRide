package org.example.tickettoride.service;

import org.example.tickettoride.dto.request.TicketRequest;
import org.example.tickettoride.dto.response.TicketOperationResponse;
import org.example.tickettoride.model.Ticket;

import java.util.List;

public interface TicketService {
    TicketOperationResponse saveTicket(TicketRequest ticket);
    Ticket findOptimalTicket(String departure, String arrival);
    int calculatePriceForTicket(int segments);
    List<Ticket> getAllTickets();
}
