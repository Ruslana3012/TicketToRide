package org.example.tickettoride.service;

import org.example.tickettoride.model.Route;

public interface RouteService {
    void createRoute(Route route);
    void updateRoute(Route route);
    void deleteRoute(Long id);
}
