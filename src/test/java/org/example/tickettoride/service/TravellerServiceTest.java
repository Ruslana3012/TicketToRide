package org.example.tickettoride.service;

import org.example.tickettoride.model.User;
import org.example.tickettoride.repository.TravellerRepository;
import org.example.tickettoride.service.impl.TravellerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravellerServiceTest {
    @InjectMocks
    private TravellerServiceImpl travellerService;
    @Mock
    private TravellerRepository travellerRepository;
    private final User user = new User();

    @BeforeEach
    void setUp() {
        user.setId(1L);
        user.setName("John");
        user.setPassword("1");
        user.setAmount(15);
    }

    @Test
    @DisplayName("Should save new traveller")
    void saveTravellerTest() {
        when(travellerRepository.save(any(User.class))).thenReturn(user);

        assertEquals(user, travellerService.saveTraveller(user));
    }

    @Test
    @DisplayName("Should return traveller by id")
    void getTravellerByIdTest() {
        when(travellerRepository.findById(1L)).thenReturn(Optional.of(user));

        User retrievedUser = travellerService.getTravellerById(1L);

        assert (retrievedUser.getId() == 1L);

        verify(travellerRepository).findById(1L);
    }

    @Test
    @DisplayName("Should return all travellers")
    void getAllTravellersTest() {
        User user1 = new User();
        user1.setId(1L);
        user1.setName("Name1");
        user1.setPassword("Password1");
        user1.setAmount(100);

        when(travellerRepository.findAll())
                .thenReturn(Arrays.asList(user, user1));

        List<User> allTravellers = travellerService.getAllTravellers();

        assertEquals(2, allTravellers.size());
        assertEquals(user, allTravellers.get(0));
        assertEquals(user1, allTravellers.get(1));
    }

    @Test
    @DisplayName("Should delete traveller")
    void deleteTravellerTest() {
        User user = new User();
        user.setId(1L);
        when(travellerRepository.findById(anyLong())).thenReturn(Optional.of(user));

        travellerService.deleteTraveller(user.getId());

        verify(travellerRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Should throw NullPointerException")
    void deleteTravellerWhenUserDoesNotExistTest() {
        when(travellerRepository.findById(anyLong())).thenReturn(null);

        assertThrows(NullPointerException.class, () -> travellerService.deleteTraveller(1L));

        verify(travellerRepository, times(0)).delete(any(User.class));
    }
}
