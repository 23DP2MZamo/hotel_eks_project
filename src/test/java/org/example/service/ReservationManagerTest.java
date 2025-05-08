package org.example.service;

import org.example.model.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationManagerTest {
    
    private ReservationManager reservationManager;
    private LocalDate checkIn;
    private LocalDate checkOut;
    
    @BeforeEach
    public void setUp() {
        reservationManager = new ReservationManager();
        checkIn = LocalDate.of(2024, 3, 1);
        checkOut = LocalDate.of(2024, 3, 5);
    }
    
    @Test
    public void testCreateReservation() {
        Reservation reservation = reservationManager.createReservation(101, "John Doe", checkIn, checkOut);
        
        assertNotNull(reservation);
        assertEquals(1, reservation.getReservationId());
        assertEquals(101, reservation.getRoomNumber());
        assertEquals("John Doe", reservation.getGuestName());
        assertEquals(checkIn, reservation.getCheckInDate());
        assertEquals(checkOut, reservation.getCheckOutDate());
        assertFalse(reservation.isCancelled());
    }
    
    @Test
    public void testGetReservationsByGuestName() {
        reservationManager.createReservation(101, "John Doe", checkIn, checkOut);
        reservationManager.createReservation(102, "John Doe", checkIn, checkOut);
        reservationManager.createReservation(103, "Jane Smith", checkIn, checkOut);
        
        List<Reservation> johnsReservations = reservationManager.getReservationsByGuestName("John Doe");
        assertEquals(2, johnsReservations.size());
        
        List<Reservation> janesReservations = reservationManager.getReservationsByGuestName("Jane Smith");
        assertEquals(1, janesReservations.size());
    }
    
    @Test
    public void testCancelReservation() {
        Reservation reservation = reservationManager.createReservation(101, "John Doe", checkIn, checkOut);
        
        assertTrue(reservationManager.cancelReservation(reservation.getReservationId()));
        assertFalse(reservationManager.cancelReservation(999)); // Non-existent reservation
    }
    
    @Test
    public void testGetReservationById() {
        Reservation created = reservationManager.createReservation(101, "John Doe", checkIn, checkOut);
        
        Reservation found = reservationManager.getReservationById(created.getReservationId());
        assertNotNull(found);
        assertEquals(created.getReservationId(), found.getReservationId());
        
        Reservation notFound = reservationManager.getReservationById(999);
        assertNull(notFound);
    }
    
    @Test
    public void testReservationCounts() {
        assertEquals(0, reservationManager.getTotalReservationCount());
        assertEquals(0, reservationManager.getActiveReservationCount());
        assertEquals(0, reservationManager.getCancelledReservationCount());
        
        Reservation reservation = reservationManager.createReservation(101, "John Doe", checkIn, checkOut);
        assertEquals(1, reservationManager.getTotalReservationCount());
        assertEquals(1, reservationManager.getActiveReservationCount());
        assertEquals(0, reservationManager.getCancelledReservationCount());
        
        reservation.setCancelled(true);
        assertEquals(1, reservationManager.getTotalReservationCount());
        assertEquals(0, reservationManager.getActiveReservationCount());
        assertEquals(1, reservationManager.getCancelledReservationCount());
    }
} 