package lv.rvt.hotel.service;

import lv.rvt.hotel.model.Room;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private List<Room> rooms;

    public RoomManager() {
        this.rooms = new ArrayList<>();
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
} 