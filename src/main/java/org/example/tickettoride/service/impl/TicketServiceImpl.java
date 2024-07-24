package org.example.tickettoride.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tickettoride.dto.request.TicketRequest;
import org.example.tickettoride.dto.response.TicketOperationResponse;
import org.example.tickettoride.model.Ticket;
import org.example.tickettoride.model.User;
import org.example.tickettoride.repository.TicketRepository;
import org.example.tickettoride.repository.TravellerRepository;
import org.example.tickettoride.service.TicketService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final TravellerRepository travellerRepository;
    private final GraphServiceImpl graphService;

    /**
     * Saves a new ticket based on the provided ticket request. Also deducts the ticket's
     * cost from the user's total amount. If the user lacks funds to cover the ticket cost,
     * the operation will not proceed and the saving operation will fail.
     *
     * @param ticketRequest the request details necessary for ticket creation and saving,
     *                      including ticket's arrival and departure points, segments, price,
     *                      and user's details.
     * @return A response detailing either the success or failure of the ticket saving operation.
     * In case of success, includes the user's remaining amount after deducting ticket price.
     * In case of failure, includes the amount the user lacks to buy the ticket.
     */
    @Override
    @Transactional
    public TicketOperationResponse saveTicket(TicketRequest ticketRequest) {
        TicketOperationResponse result = new TicketOperationResponse();

        User user = travellerRepository.findUserByName(ticketRequest.getTraveller());
        if (user.getAmount() >= ticketRequest.getPrice()) {
            user.setAmount(user.getAmount() - ticketRequest.getPrice());
            travellerRepository.save(user);

            Ticket ticket = new Ticket();
            ticket.setDeparture(ticketRequest.getDeparture());
            ticket.setArrival(ticketRequest.getArrival());
            ticket.setSegments(ticketRequest.getSegments());
            ticket.setPrice(ticketRequest.getPrice());
            ticket.setCurrency(ticketRequest.getCurrency());
            ticket.setUser(user);
            Ticket savedTicket = ticketRepository.save(ticket);

            result.setResult("success");
            result.setChange(savedTicket.getUser().getAmount());
            result.setCurrency("GBP");
        } else {
            log.info("Insufficient funds to buy a ticket");
            result.setResult("failure");
            result.setLackOf(ticketRequest.getPrice() - user.getAmount());
            result.setCurrency("GBP");
        }
        return result;
    }

    /**
     * Finds the optimal ticket for a route from a departure point to an arrival point.
     * The ticket includes details about the departure and arrival points, the number of segments in the route,
     * the price of the ticket, and the currency of the ticket cost.
     *
     * @param departure the departure point of the ticket.
     * @param arrival   the arrival point of the ticket.
     * @return the most optimal ticket from departure to arrival.
     */
    @Override
    public Ticket findOptimalTicket(String departure, String arrival) {
        int segments = graphService.findMostOptimalRoute(departure, arrival);
        Ticket ticket = new Ticket();
        ticket.setDeparture(departure);
        ticket.setArrival(arrival);
        ticket.setSegments(segments);
        ticket.setPrice(calculatePriceForTicket(segments));
        ticket.setCurrency("GBP");
        return ticket;
    }

    /**
     * Calculates the cost of a ticket based on the number of travel segments.
     * The pricing model is as follows:
     * - The price of travel through 1 segment is 5 GBP.
     * - The price of travel through 2 segments is 7 GBP.
     * - The price of travel through 3 segments is 10 GBP.
     *
     * @param segments the number of travel segments.
     * @return the cost of a ticket as an integer.
     * @throws IllegalArgumentException if the number of segments is less than or equal to 0.
     */
    @Override
    public int calculatePriceForTicket(int segments) {
        if (segments <= 0) {
            log.info("Number of segments must be positive");
            throw new IllegalArgumentException("Number of segments must be positive");
        }
        int cost = 0;
        while (segments > 0) {
            if (segments >= 3) {
                cost += 10;
                segments -= 3;
            } else if (segments == 2) {
                cost += 7;
                segments -= 2;
            } else {
                cost += 5;
                segments -= 1;
            }
        }
        return cost;
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
