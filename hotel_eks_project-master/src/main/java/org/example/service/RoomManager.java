package org.example.service;

import org.example.database.DatabaseManager;
import org.example.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Room> rooms;
    private DatabaseManager databaseManager;

    public RoomManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.rooms = databaseManager.loadRooms();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAvailableRooms() {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable()) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public Room getRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }

    public void updateRoomAvailability(int roomNumber, boolean available) {
        Room room = getRoomByNumber(roomNumber);
        if (room != null) {
            room.setAvailable(available);
        }
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }

    public int getAvailableRoomCount() {
        return (int) rooms.stream()
                .filter(Room::isAvailable)
                .count();
    }

    public int getTotalRoomCount() {
        return rooms.size();
    }
}
