package org.example.tickettoride.controller;

import lombok.RequiredArgsConstructor;
import org.example.tickettoride.dto.request.TicketRequest;
import org.example.tickettoride.dto.response.TicketOperationResponse;
import org.example.tickettoride.dto.response.TicketResponse;
import org.example.tickettoride.service.TicketService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;
    private final ModelMapper modelMapper;

    @PostMapping("/buy")
    public TicketOperationResponse buyTicket(@RequestBody TicketRequest ticketRequest) {
        return ticketService.saveTicket(ticketRequest);
    }

    @GetMapping("/find/from/{departure}/to/{arrival}")
    public TicketResponse findTicketAndCalculateThePrice(@PathVariable("departure") String departure, @PathVariable("arrival") String arrival) {
        return modelMapper.map(ticketService.findOptimalTicket(departure, arrival), TicketResponse.class);
    }
}
