package org.example.database;

import org.example.model.Room;
import org.example.model.Reservation;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String ROOMS_FILE = "src/main/resources/rooms.csv";
    private static final String RESERVATIONS_FILE = "src/main/resources/reservations.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void saveRooms(List<Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(ROOMS_FILE), StandardCharsets.UTF_8))) {
            writer.println("id,floor,type,beds,status,price,guestName");
            for (Room room : rooms) {
                String status = room.isAvailable() ? "AVAILABLE" : "OCCUPIED";
                String price = String.format("%.2f", room.getPrice()).replace('.', ',');
                String guestName = room.getGuestName() != null ? room.getGuestName() : "";
                writer.printf("%d,%d,%s,%d,%s,%s,%s%n",
                        room.getRoomNumber(),
                        room.getFloor(),
                        room.getType(),
                        room.getBeds(),
                        status,
                        price,
                        guestName);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        File file = new File(ROOMS_FILE);

        if (!file.exists()) {
            seedRooms(); // Ja nav datu, aizpilda ar sākotnējiem datiem
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(ROOMS_FILE), StandardCharsets.UTF_8))) {
            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length >= 7) {
                    int id = Integer.parseInt(parts[0]);
                    int floor = Integer.parseInt(parts[1]);
                    String type = parts[2];
                    int beds = Integer.parseInt(parts[3]);
                    boolean available = parts[4].equalsIgnoreCase("AVAILABLE");
                    double price = Double.parseDouble(parts[5].replace(",", "."));
                    String guestName = parts[6].trim();

                    Room room = new Room(id, floor, type, beds, price);
                    room.setAvailable(available);
                    room.setGuestName(guestName.isEmpty() ? null : guestName);

                    rooms.add(room);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rooms;
    }

    public void seedRooms() {
        List<Room> rooms = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            int floor = (i - 1) / 10 + 1;
            String type;
            int beds;
            double price;

            if (i <= 30) {
                type = "SINGLE";
                beds = 1;
                price = 20.00;
            } else if (i <= 50) {
                type = "DUO";
                beds = 2;
                price = 30.00;
            } else {
                type = "LUX";
                beds = 3;
                price = 40.00;
            }

            Room room = new Room(i, floor, type, beds, price);
            room.setAvailable(true);
            room.setGuestName("");
            rooms.add(room);
        }

        Room president = new Room(101, 11, "PRESIDENT", 5, 1000.00);
        president.setAvailable(true);
        president.setGuestName("");
        rooms.add(president);

        saveRooms(rooms);
    }

    public void saveReservations(List<Reservation> reservations) {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream(RESERVATIONS_FILE), StandardCharsets.UTF_8))) {
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
        File file = new File(RESERVATIONS_FILE);
        if (!file.exists()) {
            return reservations;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(RESERVATIONS_FILE), StandardCharsets.UTF_8))) {
            String line = reader.readLine();

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