package org.example.tickettoride.service;

import org.example.tickettoride.model.User;

import java.util.List;

public interface TravellerService {
    User saveTraveller(User user);
    User getTravellerById(long id);
    List<User> getAllTravellers();
    void deleteTraveller(long id);
}
