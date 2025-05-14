package org.example.ui;

import org.example.model.Room;
import org.example.model.Reservation;
import org.example.service.RoomManager;
import org.example.service.ReservationManager;
import org.example.tools.ConsoleColors;
import org.example.tools.DateValidator;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConsoleUI {
    private final RoomManager roomManager;
    private final ReservationManager reservationManager;
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ConsoleUI(RoomManager roomManager, ReservationManager reservationManager) {
        this.roomManager = roomManager;
        this.reservationManager = reservationManager;
        this.scanner = new Scanner(System.in);
    }

    // Simple method to print a line of dashes with color
    private void printLine() {
        System.out.println(ConsoleColors.CYAN + "--------------------------------------------------" + ConsoleColors.RESET);
    }

    // Simple method to print a title with color
    private void printTitle(String title) {
        printLine();
        System.out.println(ConsoleColors.CYAN_BOLD + "                 " + title + ConsoleColors.RESET);
        printLine();
    }

    public void start() {
        while (true) {
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewAllRooms();
                    break;
                case "2":
                    makeReservation();
                    break;
                case "3":
                    viewReservations();
                    break;
                case "4":
                    cancelReservation();
                    break;
                case "5":
                    displayStatistics();
                    break;
                case "6":
                    System.out.println(ConsoleColors.GREEN + "\nThank you for using the Hotel Management System!" + ConsoleColors.RESET);
                    return;
                default:
                    System.out.println(ConsoleColors.RED + "\nInvalid choice. Please try again." + ConsoleColors.RESET);
            }
        }
    }

    private void displayMenu() {
        printTitle("Hotel Management System");
        System.out.println(ConsoleColors.YELLOW + "1. View All Rooms");
        System.out.println("2. Make a Reservation");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. View Statistics");
        System.out.println("6. Exit" + ConsoleColors.RESET);
        printLine();
        System.out.print("\nEnter your choice: ");
    }

    private void viewAllRooms() {
        List<Room> rooms = roomManager.getAllRooms();
        if (rooms.isEmpty()) {
            printTitle("Room Information");
            System.out.println(ConsoleColors.YELLOW + "No rooms available!" + ConsoleColors.RESET);
            printLine();
            return;
        }

        printTitle("Available Rooms");
        System.out.println(ConsoleColors.CYAN + "Room | Floor | Type    | Beds | Status    | Price  | Guest" + ConsoleColors.RESET);
        printLine();

        for (Room room : rooms) {
            String status = room.isAvailable() ? 
                ConsoleColors.GREEN + "Available" + ConsoleColors.RESET : 
                ConsoleColors.RED + "Booked" + ConsoleColors.RESET;
            String guest = room.getGuestName() != null ? room.getGuestName() : "-";
            System.out.printf("%-4d | %-5d | %-8s | %-4d | %-9s | $%-5.2f | %s%n",
                    room.getRoomNumber(),
                    room.getFloor(),
                    room.getType(),
                    room.getBeds(),
                    status,
                    room.getPrice(),
                    guest);
        }
        printLine();
    }

    private void makeReservation() {
        printTitle("Make a Reservation");
        
        // Show available rooms
        List<Room> availableRooms = roomManager.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            System.out.println(ConsoleColors.RED + "No rooms available for reservation!" + ConsoleColors.RESET);
            printLine();
            return;
        }

        System.out.println(ConsoleColors.CYAN + "Available Rooms:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "Room | Floor | Type    | Beds | Price" + ConsoleColors.RESET);
        printLine();

        for (Room room : availableRooms) {
            System.out.printf("%-4d | %-5d | %-8s | %-4d | $%-5.2f%n",
                    room.getRoomNumber(),
                    room.getFloor(),
                    room.getType(),
                    room.getBeds(),
                    room.getPrice());
        }
        printLine();

        // Get room number
        System.out.print("\nEnter room number: ");
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(scanner.nextLine().trim());
            if (roomManager.getRoomByNumber(roomNumber) == null) {
                System.out.println(ConsoleColors.RED + "Invalid room number!" + ConsoleColors.RESET);
                return;
            }
            if (!roomManager.getRoomByNumber(roomNumber).isAvailable()) {
                System.out.println(ConsoleColors.RED + "Room is not available!" + ConsoleColors.RESET);
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Invalid room number format!" + ConsoleColors.RESET);
            return;
        }

        // Get guest name
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine().trim();
        if (guestName.isEmpty()) {
            System.out.println(ConsoleColors.RED + "Guest name cannot be empty!" + ConsoleColors.RESET);
            return;
        }

        // Get check-in date
        System.out.print("Enter check-in date (YYYY-MM-DD): ");
        String checkInStr = scanner.nextLine().trim();
        String checkInError = DateValidator.getDateValidationMessage(checkInStr);
        if (checkInError != null) {
            System.out.println(ConsoleColors.RED + checkInError + ConsoleColors.RESET);
            return;
        }

        // Get check-out date
        System.out.print("Enter check-out date (YYYY-MM-DD): ");
        String checkOutStr = scanner.nextLine().trim();
        String dateRangeError = DateValidator.getDateRangeValidationMessage(checkInStr, checkOutStr);
        if (dateRangeError != null) {
            System.out.println(ConsoleColors.RED + dateRangeError + ConsoleColors.RESET);
            return;
        }

        // Create reservation
        LocalDate checkIn = LocalDate.parse(checkInStr, DATE_FORMATTER);
        LocalDate checkOut = LocalDate.parse(checkOutStr, DATE_FORMATTER);
        
        Reservation reservation = reservationManager.createReservation(roomNumber, guestName, checkIn, checkOut);
        roomManager.getRoomByNumber(roomNumber).setAvailable(false);
        roomManager.getRoomByNumber(roomNumber).setGuestName(guestName);

        System.out.println(ConsoleColors.GREEN + "\nReservation created successfully!" + ConsoleColors.RESET);
        System.out.println("Reservation ID: " + reservation.getReservationId());
    }

    private void viewReservations() {
        printTitle("Current Reservations");
        
        List<Reservation> reservations = reservationManager.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No reservations found!" + ConsoleColors.RESET);
            printLine();
            return;
        }

        System.out.println(ConsoleColors.CYAN + "ID | Room | Guest           | Check-in    | Check-out   | Status" + ConsoleColors.RESET);
        printLine();

        for (Reservation res : reservations) {
            String status = res.isCancelled() ? 
                ConsoleColors.RED + "Cancelled" + ConsoleColors.RESET : 
                ConsoleColors.GREEN + "Active" + ConsoleColors.RESET;
            System.out.printf("%-2d | %-4d | %-15s | %-11s | %-11s | %s%n",
                    res.getReservationId(),
                    res.getRoomNumber(),
                    res.getGuestName(),
                    res.getCheckInDate().format(DATE_FORMATTER),
                    res.getCheckOutDate().format(DATE_FORMATTER),
                    status);
        }
        printLine();
    }

    private void cancelReservation() {
        printTitle("Cancel Reservation");
        
        List<Reservation> activeReservations = reservationManager.getAllReservations().stream()
                .filter(r -> !r.isCancelled())
                .toList();
        
        if (activeReservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "No active reservations found!" + ConsoleColors.RESET);
            printLine();
            return;
        }

        System.out.println(ConsoleColors.CYAN + "Active Reservations:" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.CYAN + "ID | Room | Guest           | Check-in    | Check-out" + ConsoleColors.RESET);
        printLine();

        for (Reservation res : activeReservations) {
            System.out.printf("%-2d | %-4d | %-15s | %-11s | %-11s%n",
                    res.getReservationId(),
                    res.getRoomNumber(),
                    res.getGuestName(),
                    res.getCheckInDate().format(DATE_FORMATTER),
                    res.getCheckOutDate().format(DATE_FORMATTER));
        }
        printLine();

        // Get reservation ID to cancel
        System.out.print("\nEnter reservation ID to cancel: ");
        try {
            int reservationId = Integer.parseInt(scanner.nextLine().trim());
            if (reservationManager.cancelReservation(reservationId)) {
                // Update room availability
                Reservation reservation = reservationManager.getReservationById(reservationId);
                if (reservation != null) {
                    Room room = roomManager.getRoomByNumber(reservation.getRoomNumber());
                    if (room != null) {
                        room.setAvailable(true);
                        room.setGuestName(null);
                    }
                }
                System.out.println(ConsoleColors.GREEN + "\nReservation cancelled successfully!" + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED + "\nInvalid reservation ID!" + ConsoleColors.RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "\nInvalid reservation ID format!" + ConsoleColors.RESET);
        }
    }

    private void displayStatistics() {
        printTitle("Hotel Statistics");
        
        // Room statistics
        System.out.println(ConsoleColors.CYAN_BOLD + "Room Statistics:" + ConsoleColors.RESET);
        printLine();
        
        int totalRooms = roomManager.getTotalRoomCount();
        int availableRooms = roomManager.getAvailableRoomCount();
        int occupiedRooms = totalRooms - availableRooms;
        
        System.out.println("Total Rooms: " + totalRooms);
        System.out.println("Available Rooms: " + availableRooms);
        System.out.println("Occupied Rooms: " + occupiedRooms);
        System.out.printf("Occupancy Rate: %.1f%%%n", (occupiedRooms * 100.0 / totalRooms));
        printLine();

        // Reservation statistics
        System.out.println(ConsoleColors.CYAN_BOLD + "Reservation Statistics:" + ConsoleColors.RESET);
        printLine();
        
        int totalReservations = reservationManager.getTotalReservationCount();
        int activeReservations = reservationManager.getActiveReservationCount();
        int cancelledReservations = reservationManager.getCancelledReservationCount();
        
        System.out.println("Total Reservations: " + totalReservations);
        System.out.println("Active Reservations: " + activeReservations);
        System.out.println("Cancelled Reservations: " + cancelledReservations);
        if (totalReservations > 0) {
            System.out.printf("Cancellation Rate: %.1f%%%n", 
                    (cancelledReservations * 100.0 / totalReservations));
        }
        printLine();
    }
}
