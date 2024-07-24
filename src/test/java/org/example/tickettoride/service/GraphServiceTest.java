package org.example.tickettoride.service;

import org.example.tickettoride.model.Route;
import org.example.tickettoride.repository.RouteRepository;
import org.example.tickettoride.service.impl.GraphServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GraphServiceTest {

    @Test
    @DisplayName("Should return optimal route")
    void findMostOptimalRouteTest() {
        RouteRepository mockRouteRepository = Mockito.mock(RouteRepository.class);
        List<Route> routes = new ArrayList<>();
        Route route1 = new Route();
        route1.setDeparture("Town1");
        route1.setArrival("Town2");
        route1.setSegments(1);

        Route route2 = new Route();
        route2.setDeparture("Town2");
        route2.setArrival("Town3");
        route2.setSegments(2);
        routes.add(route1);
        routes.add(route2);
        Mockito.when(mockRouteRepository.findAll()).thenReturn(routes);
        GraphServiceImpl graphService = new GraphServiceImpl(mockRouteRepository);

        int result = graphService.findMostOptimalRoute("Town1", "Town3");

        assertEquals(3, result);
    }
}
