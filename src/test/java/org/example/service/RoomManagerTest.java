package org.example.service;

import org.example.model.Room;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RoomManagerTest {
    
    private RoomManager roomManager;
    
    @BeforeEach
    public void setUp() {
        roomManager = new RoomManager();
        
        // Add some test rooms
        roomManager.addRoom(new Room(101, 1, "SINGLE", 1, 100.0));
        roomManager.addRoom(new Room(102, 1, "DUO", 2, 150.0));
        roomManager.addRoom(new Room(201, 2, "LUX", 2, 200.0));
    }
    
    @Test
    public void testGetAllRooms() {
        List<Room> rooms = roomManager.getAllRooms();
        assertEquals(3, rooms.size());
    }
    
    @Test
    public void testGetAvailableRooms() {
        List<Room> availableRooms = roomManager.getAvailableRooms();
        assertEquals(3, availableRooms.size());
        
        // Make a room unavailable
        roomManager.updateRoomAvailability(101, false);
        availableRooms = roomManager.getAvailableRooms();
        assertEquals(2, availableRooms.size());
    }
    
    @Test
    public void testGetRoomByNumber() {
        Room room = roomManager.getRoomByNumber(101);
        assertNotNull(room);
        assertEquals(101, room.getRoomNumber());
        assertEquals("SINGLE", room.getType());
        
        Room nonExistentRoom = roomManager.getRoomByNumber(999);
        assertNull(nonExistentRoom);
    }
    
    @Test
    public void testUpdateRoomAvailability() {
        Room room = roomManager.getRoomByNumber(101);
        assertTrue(room.isAvailable());
        
        roomManager.updateRoomAvailability(101, false);
        room = roomManager.getRoomByNumber(101);
        assertFalse(room.isAvailable());
        
        roomManager.updateRoomAvailability(101, true);
        room = roomManager.getRoomByNumber(101);
        assertTrue(room.isAvailable());
    }
    
    @Test
    public void testRoomCounts() {
        assertEquals(3, roomManager.getTotalRoomCount());
        assertEquals(3, roomManager.getAvailableRoomCount());
        
        roomManager.updateRoomAvailability(101, false);
        assertEquals(3, roomManager.getTotalRoomCount());
        assertEquals(2, roomManager.getAvailableRoomCount());
    }
} 