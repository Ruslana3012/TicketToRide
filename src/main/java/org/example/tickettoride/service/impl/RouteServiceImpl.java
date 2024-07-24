package org.example.tickettoride.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.tickettoride.model.Route;
import org.example.tickettoride.repository.RouteRepository;
import org.example.tickettoride.service.RouteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;

    @Override
    public void createRoute(Route route) {
        routeRepository.save(route);
    }

    @Override
    public void updateRoute(Route route) {
        routeRepository.save(route);
    }

    @Override
    public void deleteRoute(Long id) {
        routeRepository.deleteById(id);
    }
}