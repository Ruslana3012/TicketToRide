package org.example.tickettoride.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.tickettoride.model.User;
import org.example.tickettoride.repository.TravellerRepository;
import org.example.tickettoride.service.TravellerService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TravellerServiceImpl implements TravellerService {
    private final TravellerRepository travellerRepository;

    @Override
    public User saveTraveller(User user) {
        return travellerRepository.save(user);
    }

    @Override
    public User getTravellerById(long id) {
        return travellerRepository.findById(id).orElseThrow(() -> {
            log.info("Attempted to get traveller with id '{}'", id);
            return new EntityNotFoundException("Traveller with id " + id + " not found");
        });
    }

    @Override
    public List<User> getAllTravellers() {
        return travellerRepository.findAll();
    }

    @Override
    public void deleteTraveller(long id) {
        travellerRepository.delete(getTravellerById(id));
    }
}
