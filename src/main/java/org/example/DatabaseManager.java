package org.example;
import java.io.*;
import java.util.*;

public class DatabaseManager {
    private static final String FILE_PATH = "rooms.csv";
    public DatabaseManager() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("id,floor,type,beds,status,price");
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Error while creating CSV: " + e.getMessage());
            }
        }
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            reader.readLine(); // Пропустить заголовок
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Room room = new Room(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        Room.RoomType.valueOf(parts[2].toUpperCase()),
                        Integer.parseInt(parts[3]),
                        Room.RoomStatus.valueOf(parts[4].toUpperCase()),
                        Float.parseFloat(parts[5]),
                        parts.length > 6 ? parts[6] : ""
                );
                rooms.add(room);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return rooms;
    }

    public void updateRooms(List<Room> rooms) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("id,floor,type,beds,status,price,guestName\n");
            for (Room room : rooms) {
                writer.write(String.format("%d,%d,%s,%d,%s,%.2f,%s\n",
                        room.getId(), room.getFloor(), room.getType(),
                        room.getBeds(), room.getStatus(), room.getPrice(),
                        room.getGuestName() != null ? room.getGuestName() : ""
                ));
            }
        } catch (IOException e) {
            System.err.println("Error writing in CSV: " + e.getMessage());
        }
    }
}