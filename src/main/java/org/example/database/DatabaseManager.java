package org.example.database;

import org.example.model.Room;
import org.example.model.Reservation;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String ROOMS_FILE = "src/main/resources/rooms.csv";
    private static final String RESERVATIONS_FILE = "src/main/resources/reservations.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private void seedRooms() {
        List<Room> rooms = new ArrayList<>();
        
        // Standard Rooms
        rooms.add(new Room(101, 1, "Standard", 1, 100.0));
        rooms.add(new Room(102, 1, "Standard", 1, 100.0));
        rooms.add(new Room(103, 1, "Standard", 2, 150.0));
        rooms.add(new Room(104, 1, "Standard", 2, 150.0));
        
        // Deluxe Rooms
        rooms.add(new Room(201, 2, "Deluxe", 1, 200.0));
        rooms.add(new Room(202, 2, "Deluxe", 1, 200.0));
        rooms.add(new Room(203, 2, "Deluxe", 2, 250.0));
        rooms.add(new Room(204, 2, "Deluxe", 2, 250.0));
        
        // Suite Rooms
        rooms.add(new Room(301, 3, "Suite", 2, 300.0));
        rooms.add(new Room(302, 3, "Suite", 2, 300.0));
        rooms.add(new Room(303, 3, "Suite", 3, 400.0));
        rooms.add(new Room(304, 3, "Suite", 3, 400.0));
        
        // Presidential Suite
        rooms.add(new Room(401, 4, "Presidential Suite", 4, 1000.0));
        
        // Create resources directory if it doesn't exist
        File resourcesDir = new File("src/main/resources");
        if (!resourcesDir.exists()) {
            resourcesDir.mkdirs();
        }
        
        saveRooms(rooms);
    }

    public void saveRooms(List<Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            writer.println("roomNumber,floor,type,beds,price,available,guestName");
            for (Room room : rooms) {
                writer.printf("%d,%d,%s,%d,%.2f,%b,%s%n",
                    room.getRoomNumber(),
                    room.getFloor(),
                    room.getType(),
                    room.getBeds(),
                    room.getPrice(),
                    room.isAvailable(),
                    room.getGuestName() != null ? room.getGuestName() : "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(ROOMS_FILE);
        
        if (!file.exists()) {
            seedRooms();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    Room room = new Room(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        Integer.parseInt(parts[3]),
                        Double.parseDouble(parts[4])
                    );
                    room.setAvailable(Boolean.parseBoolean(parts[5]));
                    if (!parts[6].isEmpty()) {
                        room.setGuestName(parts[6]);
                    }
                    rooms.add(room);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rooms;
    }

    public void saveReservations(List<Reservation> reservations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
            writer.println("id,roomNumber,guestName,checkInDate,checkOutDate,cancelled");
            for (Reservation reservation : reservations) {
                writer.printf("%d,%d,%s,%s,%s,%b%n",
                    reservation.getReservationId(),
                    reservation.getRoomNumber(),
                    reservation.getGuestName(),
                    reservation.getCheckInDate().format(DATE_FORMATTER),
                    reservation.getCheckOutDate().format(DATE_FORMATTER),
                    reservation.isCancelled());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line = reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    Reservation reservation = new Reservation(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        LocalDate.parse(parts[3], DATE_FORMATTER),
                        LocalDate.parse(parts[4], DATE_FORMATTER)
                    );
                    reservation.setCancelled(Boolean.parseBoolean(parts[5]));
                    reservations.add(reservation);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reservations;
    }
} 