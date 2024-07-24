package org.example.tickettoride.dto.request;

import lombok.Data;

/**
 * The TicketRequest class represents a request to create and save a ticket.
 * It contains the necessary information for ticket creation, such as departure and arrival points,
 * number of segments, price, and currency.
 * Despite task requirements, the travellerAmount field is commented out because the application
 * uses authorization and retrieves the traveller's balance from the user object.
 */
@Data
public class TicketRequest {
    private String departure;
    private String arrival;
    private int segments;
    private int price;
    private String currency;
    // private int travellerAmount;
    private String traveller;
}
