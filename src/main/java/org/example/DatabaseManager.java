package org.example;

import java.io.*;
import java.util.*;

public class DatabaseManager {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/rooms.csv";

    public DatabaseManager() {
        File file = new File(FILE_PATH);
        System.out.println("Database file path: " + FILE_PATH);

        // Create directory if it doesn't exist
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("id,floor,type,beds,status,price,guestName\n");
                System.out.println("Created new rooms.csv file");
            } catch (IOException e) {
                System.err.println("Error while creating CSV: " + e.getMessage());
            }
        }
    }

    public List<Room> getAllRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(FILE_PATH);
        System.out.println("Reading from file: " + file.getAbsolutePath());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String header = reader.readLine();
            System.out.println("CSV Header: " + header);

            String line;
            int lineNumber = 1;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) {
                        System.err.println("Warning: Invalid line " + lineNumber + ": " + line);
                        continue;
                    }

                    String guestName = parts.length > 6 ? parts[6].trim() : "";
                    if (guestName.equals("00") || guestName.isEmpty()) {
                        guestName = null;
                    }

                    Room room = new Room(
                            Integer.parseInt(parts[0]),
                            Integer.parseInt(parts[1]),
                            Room.RoomType.valueOf(parts[2].toUpperCase()),
                            Integer.parseInt(parts[3]),
                            Room.RoomStatus.valueOf(parts[4].toUpperCase()),
                            Float.parseFloat(parts[5]),
                            guestName
                    );
                    rooms.add(room);
                } catch (Exception e) {
                    System.err.println("Error parsing line " + lineNumber + ": " + line);
                    System.err.println("Error details: " + e.getMessage());
                }
            }
            System.out.println("Successfully loaded " + rooms.size() + " rooms from database");
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return rooms;
    }

    public void updateRooms(List<Room> rooms) {
        System.out.println("Updating " + rooms.size() + " rooms in database");
        saveRooms(rooms);
    }

    public void saveRooms(List<Room> rooms) {
        File file = new File(FILE_PATH);
        System.out.println("Saving to file: " + file.getAbsolutePath());

        try {
            // Create a temporary file
            File tempFile = new File(FILE_PATH + ".tmp");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write("id,floor,type,beds,status,price,guestName\n");
                for (Room room : rooms) {
                    String guestName = room.getGuestName() != null ? room.getGuestName() : "";
                    String line = String.format("%d,%d,%s,%d,%s,%.2f,%s\n",
                            room.getId(), room.getFloor(), room.getType(),
                            room.getBeds(), room.getStatus(), room.getPrice(),
                            guestName);
                    writer.write(line);
                }
            }

            // Replace the original file with the temporary file
            if (file.exists()) {
                file.delete();
            }
            tempFile.renameTo(file);

            System.out.println("Successfully saved " + rooms.size() + " rooms to database");
            System.out.println("File size: " + file.length() + " bytes");
        } catch (IOException e) {
            System.err.println("Error saving rooms: " + e.getMessage());
            e.printStackTrace();
        }
    }
}