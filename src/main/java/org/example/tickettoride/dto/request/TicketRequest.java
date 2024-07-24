package org.example.tickettoride.dto.request;

import lombok.Data;

@Data
public class TicketRequest {
    private String departure;
    private String arrival;
    private int segments;
    private int price;
    private String currency;
//    private int travellerAmount;
    private String traveller;
}
