package org.example.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {
    
    @Test
    public void testReservationCreation() {
        LocalDate checkIn = LocalDate.of(2024, 3, 1);
        LocalDate checkOut = LocalDate.of(2024, 3, 5);
        
        Reservation reservation = new Reservation(1, 101, "John Doe", checkIn, checkOut);
        
        assertEquals(1, reservation.getReservationId());
        assertEquals(101, reservation.getRoomNumber());
        assertEquals("John Doe", reservation.getGuestName());
        assertEquals(checkIn, reservation.getCheckInDate());
        assertEquals(checkOut, reservation.getCheckOutDate());
        assertFalse(reservation.isCancelled());
    }
    
    @Test
    public void testReservationCancellation() {
        LocalDate checkIn = LocalDate.of(2024, 3, 1);
        LocalDate checkOut = LocalDate.of(2024, 3, 5);
        
        Reservation reservation = new Reservation(1, 101, "John Doe", checkIn, checkOut);
        
        reservation.setCancelled(true);
        assertTrue(reservation.isCancelled());
        
        reservation.setCancelled(false);
        assertFalse(reservation.isCancelled());
    }
    
    @Test
    public void testCheckOutDateUpdate() {
        LocalDate checkIn = LocalDate.of(2024, 3, 1);
        LocalDate checkOut = LocalDate.of(2024, 3, 5);
        LocalDate newCheckOut = LocalDate.of(2024, 3, 7);
        
        Reservation reservation = new Reservation(1, 101, "John Doe", checkIn, checkOut);
        
        reservation.setCheckOutDate(newCheckOut);
        assertEquals(newCheckOut, reservation.getCheckOutDate());
    }
} 