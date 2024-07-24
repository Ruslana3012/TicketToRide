package org.example.tickettoride.service;

import org.example.tickettoride.model.Route;
import org.example.tickettoride.repository.RouteRepository;
import org.example.tickettoride.service.impl.RouteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RouteServiceTest {
    @Mock
    private RouteRepository routeRepository;
    @InjectMocks
    private RouteServiceImpl routeService;
    private final Route route = new Route();

    @BeforeEach
    public void setUp() {
        route.setDeparture("From");
        route.setArrival("To");
        route.setSegments(5);
    }

    @Test
    @DisplayName("Should create route")
    void createRouteTest() {
        when(routeRepository.save(any(Route.class))).thenReturn(route);

        routeService.createRoute(route);

        verify(routeRepository).save(route);
    }

    @Test
    @DisplayName("Should update route")
    void updateRouteTest() {
        when(routeRepository.save(any(Route.class))).thenReturn(route);

        routeService.updateRoute(route);

        verify(routeRepository).save(route);
    }

    @Test
    @DisplayName("Should delete route")
    void deleteRouteTest() {
        Long id = 1L;

        routeService.deleteRoute(id);

        verify(routeRepository).deleteById(id);
    }
}
