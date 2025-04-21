package org.example;
import java.util.List;
import java.util.Scanner;

public class RoomManager {
    private DatabaseManager db;

    public RoomManager(DatabaseManager db) {
        this.db = db;
    }

    public void showAvailableRooms() {
        List<Room> rooms = db.getAllRooms();
        int counter = 1;
        for (Room room : rooms) {
            if (room.getStatus() == Room.RoomStatus.AVAILABLE) {
                System.out.println(counter++ + ". " + room);
            }
        }
    }

    public void findReservationsByName(String guestName) {
        List<Room> rooms = db.getAllRooms();
        boolean found = false;

        for (Room room : rooms) {
            if (room.getGuestName() != null && room.getGuestName().equalsIgnoreCase(guestName)) {
                System.out.println("Booked room ID: " + room.getId() + " | Floor: " + room.getFloor() +
                        " | Type: " + room.getType() + " | Price: " + room.getPrice());
                found = true;
            }
        }

        if (!found) {
            System.out.println("Bookings for a name \"" + guestName + "\" are not found.");
        }
    }
    public void cancelReservationByName(String guestName) {
        List<Room> rooms = db.getAllRooms();
        boolean cancelled = false;

        for (Room room : rooms) {
            if (room.getGuestName() != null && room.getGuestName().equalsIgnoreCase(guestName)) {
                room.setStatus(Room.RoomStatus.AVAILABLE);
                room.setGuestName(null);
                cancelled = true;
            }
        }

        if (cancelled) {
            db.updateRooms(rooms);
            System.out.println("Booking for a name \"" + guestName + "\" been canceled.");
        } else {
            System.out.println("Bookings for your name aro not found.");
        }
    }


    public boolean bookRoom(int roomId, String guestName) {
        List<Room> rooms = db.getAllRooms();
        for (Room room : rooms) {
            if (room.getId() == roomId && room.getStatus() == Room.RoomStatus.AVAILABLE) {
                room.setStatus(Room.RoomStatus.BOOKED);
                room.setGuestName(guestName);
                db.updateRooms(rooms);
                System.out.println("Successfully booked for a name: " + guestName);
                return true;
            }
        }

        System.out.println("Room does not exists.");
        return false;
    }
}