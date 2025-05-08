package org.example;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        System.out.println("\nSearching for bookings under name: " + guestName);
        System.out.println("Total rooms in database: " + rooms.size());

        for (Room room : rooms) {
            String currentGuestName = room.getGuestName();
            System.out.println("Checking room " + room.getId() +
                    " - Guest: " + (currentGuestName != null ? currentGuestName : "null") +
                    " - Status: " + room.getStatus());

            if (currentGuestName != null && currentGuestName.trim().equalsIgnoreCase(guestName.trim())) {
                System.out.println("\nBooked Room Details:");
                System.out.println("Room ID: " + room.getId());
                System.out.println("Floor: " + room.getFloor());
                System.out.println("Type: " + room.getType());
                System.out.println("Beds: " + room.getBeds());
                System.out.println("Price: " + room.getPrice());
                System.out.println("Status: " + room.getStatus());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No bookings found for name: \"" + guestName + "\"");
        }
    }

    public void cancelReservationByName(String guestName) {
        List<Room> rooms = db.getAllRooms();
        boolean cancelled = false;
        System.out.println("\nAttempting to cancel bookings for: " + guestName);

        for (Room room : rooms) {
            String currentGuestName = room.getGuestName();
            if (currentGuestName != null && currentGuestName.trim().equalsIgnoreCase(guestName.trim())) {
                room.setStatus(Room.RoomStatus.AVAILABLE);
                room.setGuestName(null);
                cancelled = true;
                System.out.println("Cancelled booking for room " + room.getId());
            }
        }

        if (cancelled) {
            db.updateRooms(rooms);
            System.out.println("Successfully cancelled all bookings for: " + guestName);
        } else {
            System.out.println("No bookings found for name: \"" + guestName + "\"");
        }
    }

    public boolean bookRoom(int roomId, String guestName) {
        List<Room> rooms = db.getAllRooms();
        System.out.println("\nAttempting to book room " + roomId + " for " + guestName);

        for (Room room : rooms) {
            if (room.getId() == roomId) {
                System.out.println("Found room " + roomId + ". Current status: " + room.getStatus());
                if (room.getStatus() == Room.RoomStatus.AVAILABLE) {
                    room.setStatus(Room.RoomStatus.BOOKED);
                    room.setGuestName(guestName.trim());

                    // Save the updated rooms list
                    db.updateRooms(rooms);

                    // Verify the booking was saved
                    List<Room> updatedRooms = db.getAllRooms();
                    for (Room updatedRoom : updatedRooms) {
                        if (updatedRoom.getId() == roomId) {
                            System.out.println("Verification - Room " + roomId + " status: " + updatedRoom.getStatus());
                            System.out.println("Verification - Room " + roomId + " guest: " + updatedRoom.getGuestName());
                            break;
                        }
                    }

                    System.out.println("Successfully booked room " + roomId + " for " + guestName);
                    return true;
                } else {
                    System.out.println("Room " + roomId + " is not available. Current status: " + room.getStatus());
                }
            }
        }
        System.out.println("Room " + roomId + " does not exist.");
        return false;
    }
    public List<Room> getBookings(String guestName) {
        List <Room> rooms = db.getAllRooms();
        return rooms.stream()
                .filter(room -> room.getStatus() == Room.RoomStatus.BOOKED
                        && room.getGuestName() != null
                        && room.getGuestName().equalsIgnoreCase(guestName.trim()))
                .collect(Collectors.toList());
    }

    public boolean isRoomAvailable(int roomId) {
        List<Room> rooms = db.getAllRooms();
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                return room.getStatus() == Room.RoomStatus.AVAILABLE;
            }
        }
        return false;
    }

    public void updateRoomStatus(int roomId, Room.RoomStatus status) {
        List<Room> rooms = db.getAllRooms();
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                room.setStatus(status);
                break;
            }
        }
        db.updateRooms(rooms);
    }
}