package lv.rvt.hotel.database;

import lv.rvt.hotel.model.Room;
import lv.rvt.hotel.model.Reservation;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String ROOMS_FILE = "rooms.csv";
    private static final String RESERVATIONS_FILE = "reservations.csv";

    public void saveRooms(List<Room> rooms) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ROOMS_FILE))) {
            for (Room room : rooms) {
                writer.printf("%d,%s,%.2f,%b\n",
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPrice(),
                    room.isAvailable());
            }
        } catch (IOException e) {
            System.err.println("Kļūda saglabājot istabas: " + e.getMessage());
        }
    }

    public List<Room> loadRooms() {
        List<Room> rooms = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ROOMS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Room room = new Room(
                        Integer.parseInt(parts[0]),
                        parts[1],
                        Double.parseDouble(parts[2])
                    );
                    room.setAvailable(Boolean.parseBoolean(parts[3]));
                    rooms.add(room);
                }
            }
        } catch (IOException e) {
            System.err.println("Kļūda ielādējot istabas: " + e.getMessage());
        }
        return rooms;
    }

    public void saveReservations(List<Reservation> reservations) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(RESERVATIONS_FILE))) {
            for (Reservation reservation : reservations) {
                writer.printf("%d,%d,%s,%s,%s\n",
                    reservation.getReservationId(),
                    reservation.getRoomNumber(),
                    reservation.getGuestName(),
                    reservation.getCheckInDate(),
                    reservation.getCheckOutDate());
            }
        } catch (IOException e) {
            System.err.println("Kļūda saglabājot rezervācijas: " + e.getMessage());
        }
    }

    public List<Reservation> loadReservations() {
        List<Reservation> reservations = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(RESERVATIONS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Reservation reservation = new Reservation(
                        Integer.parseInt(parts[0]),
                        Integer.parseInt(parts[1]),
                        parts[2],
                        parts[3],
                        parts[4]
                    );
                    reservations.add(reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("Kļūda ielādējot rezervācijas: " + e.getMessage());
        }
        return reservations;
    }
} 