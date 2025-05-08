package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationManager {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/reservations.csv";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final RoomManager roomManager;

    public ReservationManager(RoomManager roomManager) {
        this.roomManager = roomManager;
        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        if (!file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("roomId,guestName,checkInDate,checkOutDate,isActive\n");
                System.out.println("Created new reservations.csv file");
            } catch (IOException e) {
                System.err.println("Error while creating reservations.csv: " + e.getMessage());
            }
        }
    }

    public void makeReservation(int roomId, String guestName) {
        if (!roomManager.isRoomAvailable(roomId)) {
            System.out.println("Room " + roomId + " is not available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        LocalDate checkInDate = getDateFromUser(scanner, "Enter check-in date");
        LocalDate checkOutDate = getDateFromUser(scanner, "Enter check-out date");

        if (checkOutDate.isBefore(checkInDate)) {
            System.out.println("Check-out date cannot be before check-in date.");
            return;
        }

        Reservation reservation = new Reservation(roomId, guestName, checkInDate, checkOutDate);
        saveReservation(reservation);
        roomManager.updateRoomStatus(roomId, Room.RoomStatus.BOOKED);
        System.out.println("Successfully booked room " + roomId + " for " + guestName);
    }

    public void findReservationsByName(String guestName) {
        List<Reservation> reservations = getReservationsByName(guestName);
        if (reservations.isEmpty()) {
            System.out.println("No active reservations found for " + guestName);
            return;
        }

        System.out.println("\nActive reservations for " + guestName + ":");
        for (Reservation reservation : reservations) {
            System.out.println(reservation);
        }
    }

    public void cancelReservationByName(String guestName) {
        List<Reservation> reservations = getReservationsByName(guestName);
        if (reservations.isEmpty()) {
            System.out.println("No active reservations found for " + guestName);
            return;
        }

        System.out.println("\nYour active reservations:");
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println((i + 1) + ". " + reservations.get(i));
        }

        System.out.print("\nEnter the number of the reservation to cancel (or 'all' to cancel all): ");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.equalsIgnoreCase("all")) {
            for (Reservation reservation : reservations) {
                cancelReservation(reservation.getRoomId(), guestName);
                roomManager.updateRoomStatus(reservation.getRoomId(), Room.RoomStatus.AVAILABLE);
            }
            System.out.println("Cancelled all reservations for " + guestName);
        } else {
            try {
                int choice = Integer.parseInt(input) - 1;
                if (choice >= 0 && choice < reservations.size()) {
                    Reservation reservation = reservations.get(choice);
                    cancelReservation(reservation.getRoomId(), guestName);
                    roomManager.updateRoomStatus(reservation.getRoomId(), Room.RoomStatus.AVAILABLE);
                    System.out.println("Cancelled reservation for room " + reservation.getRoomId());
                } else {
                    System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number or 'all'.");
            }
        }
    }

    public void saveReservation(Reservation reservation) {
        List<Reservation> reservations = getAllReservations();
        reservations.add(reservation);
        saveAllReservations(reservations);
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        File file = new File(FILE_PATH);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    Reservation reservation = new Reservation(
                            Integer.parseInt(parts[0]),
                            parts[1],
                            LocalDate.parse(parts[2], DATE_FORMATTER),
                            LocalDate.parse(parts[3], DATE_FORMATTER)
                    );
                    reservation.setActive(Boolean.parseBoolean(parts[4]));
                    reservations.add(reservation);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading reservations: " + e.getMessage());
        }
        return reservations;
    }

    private void saveAllReservations(List<Reservation> reservations) {
        File file = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("roomId,guestName,checkInDate,checkOutDate,isActive\n");
            for (Reservation reservation : reservations) {
                writer.write(String.format("%d,%s,%s,%s,%b\n",
                        reservation.getRoomId(),
                        reservation.getGuestName(),
                        reservation.getCheckInDate().format(DATE_FORMATTER),
                        reservation.getCheckOutDate().format(DATE_FORMATTER),
                        reservation.isActive()));
            }
        } catch (IOException e) {
            System.err.println("Error saving reservations: " + e.getMessage());
        }
    }

    public List<Reservation> getReservationsByName(String guestName) {
        List<Reservation> allReservations = getAllReservations();
        List<Reservation> guestReservations = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            if (reservation.getGuestName().equalsIgnoreCase(guestName.trim()) && reservation.isActive()) {
                guestReservations.add(reservation);
            }
        }
        return guestReservations;
    }

    public void cancelReservation(int roomId, String guestName) {
        List<Reservation> reservations = getAllReservations();
        boolean cancelled = false;

        for (Reservation reservation : reservations) {
            if (reservation.getRoomId() == roomId &&
                    reservation.getGuestName().equalsIgnoreCase(guestName.trim()) &&
                    reservation.isActive()) {
                reservation.setActive(false);
                cancelled = true;
            }
        }

        if (cancelled) {
            saveAllReservations(reservations);
            System.out.println("Successfully cancelled reservation for room " + roomId);
        } else {
            System.out.println("No active reservation found for room " + roomId);
        }
    }

    public static LocalDate getDateFromUser(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (YYYY-MM-DD): ");
            String dateStr = scanner.nextLine();
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (Exception e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD");
            }
        }
    }
}