package org.example.tickettoride.repository;

import org.example.tickettoride.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravellerRepository extends JpaRepository<User, Long> {
    User findUserByName(String name);
}