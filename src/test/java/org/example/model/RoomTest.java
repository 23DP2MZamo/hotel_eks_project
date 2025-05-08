package org.example.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {
    
    @Test
    public void testRoomCreation() {
        Room room = new Room(101, 1, "SINGLE", 1, 100.0);
        
        assertEquals(101, room.getRoomNumber());
        assertEquals(1, room.getFloor());
        assertEquals("SINGLE", room.getType());
        assertEquals(1, room.getBeds());
        assertEquals(100.0, room.getPrice());
        assertTrue(room.isAvailable());
        assertNull(room.getGuestName());
    }
    
    @Test
    public void testRoomAvailability() {
        Room room = new Room(101, 1, "SINGLE", 1, 100.0);
        
        room.setAvailable(false);
        assertFalse(room.isAvailable());
        
        room.setAvailable(true);
        assertTrue(room.isAvailable());
    }
    
    @Test
    public void testGuestAssignment() {
        Room room = new Room(101, 1, "SINGLE", 1, 100.0);
        
        room.setGuestName("John Doe");
        assertEquals("John Doe", room.getGuestName());
        
        room.setGuestName(null);
        assertNull(room.getGuestName());
    }
} 