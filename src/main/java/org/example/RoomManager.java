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
        for (Room room : rooms) {
            if (room.getStatus() == Room.RoomStatus.AVAILABLE) {
                System.out.println(room);
            }
        }
    }

    public boolean bookRoom(int roomId, String guestName) {
        List<Room> rooms = db.getAllRooms();
        for (Room room : rooms) {
            if (room.getId() == roomId && room.getStatus() == Room.RoomStatus.AVAILABLE) {
                room.setStatus(Room.RoomStatus.BOOKED);
                db.updateRooms(rooms);
                System.out.println("Успешно забронировано на имя: " + guestName);
                return true;
            }
        }
        System.out.println("Комната недоступна или не существует.");
        return false;
    }
}