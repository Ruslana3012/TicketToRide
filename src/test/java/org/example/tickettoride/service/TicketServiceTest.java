package org.example.tickettoride.service;

import org.example.tickettoride.dto.request.TicketRequest;
import org.example.tickettoride.dto.response.TicketOperationResponse;
import org.example.tickettoride.model.Ticket;
import org.example.tickettoride.model.User;
import org.example.tickettoride.repository.TicketRepository;
import org.example.tickettoride.repository.TravellerRepository;
import org.example.tickettoride.service.impl.GraphServiceImpl;
import org.example.tickettoride.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {
    @InjectMocks
    private TicketServiceImpl ticketService;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TravellerRepository travellerRepository;
    @Mock
    private GraphServiceImpl graphService;

    @Test
    @DisplayName("Should save ticket")
    void saveTicketTest() {
        TicketRequest mockRequest = new TicketRequest();
        mockRequest.setTraveller("John");
        mockRequest.setPrice(30);
        mockRequest.setDeparture("A");
        mockRequest.setArrival("A");
        mockRequest.setCurrency("A");

        User mockUser = new User();
        mockUser.setName("John");
        mockUser.setAmount(50);

        Ticket mockTicket = new Ticket();
        mockTicket.setUser(mockUser);

        when(travellerRepository.findUserByName(any())).thenReturn(mockUser);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(mockTicket);

        TicketOperationResponse response = ticketService.saveTicket(mockRequest);

        assertEquals("success", response.getResult());
        assertEquals(20, response.getChange());
        assertEquals("GBP", response.getCurrency());
    }

    @Test
    @DisplayName("Should not save ticket when user has not enough funds")
    void saveTicketWhenUserHasNotEnoughFundsTest() {
        TicketRequest mockRequest = new TicketRequest();
        mockRequest.setTraveller("John");
        mockRequest.setPrice(30);

        User mockUser = new User();
        mockUser.setName("John");
        mockUser.setAmount(20);

        when(travellerRepository.findUserByName(any())).thenReturn(mockUser);

        TicketOperationResponse response = ticketService.saveTicket(mockRequest);

        assertEquals("failure", response.getResult());
        assertEquals(10, response.getLackOf());
        assertEquals("GBP", response.getCurrency());
    }

    @Test
    @DisplayName("Should throw NullPointerException")
    void saveTicketNullPointerExceptionTest() {
        assertThrows(NullPointerException.class, () -> {
            ticketService.saveTicket(null);
        });
    }

    @Test
    @DisplayName("Should find optimal ticket")
    void findOptimalTicketTest() {
        String departure = "London";
        String arrival = "Liverpool";
        int segments = 5;
        when(graphService.findMostOptimalRoute(departure, arrival)).thenReturn(segments);
        Ticket expectedTicket = new Ticket();
        expectedTicket.setDeparture(departure);
        expectedTicket.setArrival(arrival);
        expectedTicket.setSegments(segments);
        expectedTicket.setPrice(17);
        expectedTicket.setCurrency("GBP");

        Ticket actualTicket = ticketService.findOptimalTicket(departure, arrival);

        assertEquals(expectedTicket, actualTicket);
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException")
    void findOptimalTicketIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> ticketService.findOptimalTicket("", ""));
    }

    @Test
    @DisplayName("Should calculate the price for ticket")
    void calculateThePriceForTicketTest() {
        int segments = 4;
        assertEquals(15, ticketService.calculatePriceForTicket(segments));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException")
    void calculateThePriceForTicketIllegalArgumentExceptionTest() {
        int segments = -1;
        assertThrows(IllegalArgumentException.class, () -> ticketService.calculatePriceForTicket(segments));
    }

    @Test
    @DisplayName("Should get all tickets")
    void getAllTicketsTest() {
        Ticket ticket1 = new Ticket();
        Ticket ticket2 = new Ticket();
        Ticket ticket3 = new Ticket();

        List<Ticket> expectedTickets = Arrays.asList(ticket1, ticket2, ticket3);
        when(ticketRepository.findAll()).thenReturn(expectedTickets);

        List<Ticket> actualTickets = ticketService.getAllTickets();

        assertEquals(expectedTickets.size(), actualTickets.size());
        assertEquals(expectedTickets, actualTickets);
    }
}
