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

public class ConsoleUI {
    private final RoomManager roomManager;
    private final ReservationManager reservationManager;
    private final Scanner scanner;

    public ConsoleUI(RoomManager roomManager, ReservationManager reservationManager) {
        this.roomManager = roomManager;
        this.reservationManager = reservationManager;
        this.scanner = new Scanner(System.in);
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

    private void clearScreen() {
        System.out.print(ConsoleColors.CLEAR_SCREEN);
    }

    private void displayMenu() {
        System.out.println("\n" + ConsoleColors.CYAN + "+----------------------------------+");
        System.out.println("|        Hotel Management System        |");
        System.out.println("+----------------------------------+" + ConsoleColors.RESET);
        System.out.println(ConsoleColors.YELLOW + "1. View All Rooms");
        System.out.println("2. Make a Reservation");
        System.out.println("3. View Reservations");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. View Statistics");
        System.out.println("6. Exit" + ConsoleColors.RESET);
        System.out.print("\nEnter your choice: ");
    }

    private void displayStatistics() {
        clearScreen();
        System.out.println("\n" + ConsoleColors.CYAN + "+----------------------------------+");
        System.out.println("|           System Statistics          |");
        System.out.println("+----------------------------------+" + ConsoleColors.RESET);

        int totalRooms = roomManager.getTotalRoomCount();
        int availableRooms = roomManager.getAvailableRoomCount();
        int totalReservations = reservationManager.getTotalReservationCount();
        int activeReservations = reservationManager.getActiveReservationCount();
        int cancelledReservations = reservationManager.getCancelledReservationCount();

        System.out.println("\n" + ConsoleColors.YELLOW + "Room Statistics:");
        System.out.println("+----------------------------------+");
        System.out.println("| Total Rooms: " + ConsoleColors.WHITE + String.format("%-20d", totalRooms) + ConsoleColors.YELLOW + " |");
        System.out.println("| Available Rooms: " + ConsoleColors.GREEN + String.format("%-15d", availableRooms) + ConsoleColors.YELLOW + " |");
        System.out.println("| Occupied Rooms: " + ConsoleColors.RED + String.format("%-16d", (totalRooms - availableRooms)) + ConsoleColors.YELLOW + " |");
        System.out.println("+----------------------------------+");

        System.out.println("\n" + ConsoleColors.YELLOW + "Reservation Statistics:");
        System.out.println("+----------------------------------+");
        System.out.println("| Total Reservations: " + ConsoleColors.WHITE + String.format("%-12d", totalReservations) + ConsoleColors.YELLOW + " |");
        System.out.println("| Active Reservations: " + ConsoleColors.GREEN + String.format("%-11d", activeReservations) + ConsoleColors.YELLOW + " |");
        System.out.println("| Cancelled Reservations: " + ConsoleColors.RED + String.format("%-8d", cancelledReservations) + ConsoleColors.YELLOW + " |");
        System.out.println("+----------------------------------+");

        System.out.println("\n" + ConsoleColors.YELLOW + "Press Enter to return to the main menu...");
        scanner.nextLine();
    }

    private void viewAllRooms() {
        List<Room> rooms = roomManager.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo rooms available!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Available Rooms ===" + ConsoleColors.RESET);
        printRoomTable(rooms);
    }

    private void printRoomTable(List<Room> rooms) {
        final int ROOM_WIDTH = 6;
        final int FLOOR_WIDTH = 7;
        final int TYPE_WIDTH = 12;
        final int BEDS_WIDTH = 6;
        final int STATUS_WIDTH = 12;
        final int PRICE_WIDTH = 10;
        final int GUEST_WIDTH = 20;

        String headerFormat = String.format("%%-%ds %%-%ds %%-%ds %%-%ds %%-%ds %%-%ds %%-%ds",
            ROOM_WIDTH, FLOOR_WIDTH, TYPE_WIDTH, BEDS_WIDTH, STATUS_WIDTH, PRICE_WIDTH, GUEST_WIDTH);
        String rowFormat = String.format("%%-%dd %%-%dd %%-%ds %%-%dd %%-%ds $%%-%d.2f %%-%ds",
            ROOM_WIDTH, FLOOR_WIDTH, TYPE_WIDTH, BEDS_WIDTH, STATUS_WIDTH, PRICE_WIDTH, GUEST_WIDTH);

        String header = String.format(headerFormat,
            "Room", "Floor", "Type", "Beds", "Status", "Price", "Guest");
        
        int tableWidth = header.length() + 4; // Add padding for borders
        
        // Print top border
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        
        // Print header
        System.out.println("| " + ConsoleColors.CYAN + header + ConsoleColors.RESET + " |");
        
        // Print separator
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        
        // Print rows
        for (Room room : rooms) {
            String status = room.isAvailable() ? 
                ConsoleColors.GREEN + "Available" + ConsoleColors.RESET : 
                ConsoleColors.RED + "Booked" + ConsoleColors.RESET;
            
            String row = String.format(rowFormat,
                room.getRoomNumber(),
                room.getFloor(),
                room.getType(),
                room.getBeds(),
                status,
                room.getPrice(),
                room.getGuestName() != null ? room.getGuestName() : "—");
            
            System.out.println("| " + row + " |");
        }
        
        // Print bottom border
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

    private void makeReservation() {
        viewAllRooms();
        int roomNumber = getIntInput("\nEnter room number: ");
        String guestName = getStringInput("Enter guest name: ");
        
        String checkInDate;
        String checkInMessage;
        do {
            checkInDate = getStringInput("Enter check-in date (YYYY-MM-DD): ");
            checkInMessage = DateValidator.getDateValidationMessage(checkInDate);
            if (checkInMessage != null) {
                System.out.println(ConsoleColors.RED + "✗ " + checkInMessage + ConsoleColors.RESET);
            }
        } while (checkInMessage != null);

        String checkOutDate;
        String rangeMessage;
        do {
            checkOutDate = getStringInput("Enter check-out date (YYYY-MM-DD): ");
            rangeMessage = DateValidator.getDateRangeValidationMessage(checkInDate, checkOutDate);
            if (rangeMessage != null) {
                System.out.println(ConsoleColors.RED + "✗ " + rangeMessage + ConsoleColors.RESET);
            }
        } while (rangeMessage != null);

        Room room = roomManager.getRoomByNumber(roomNumber);
        if (room != null && room.isAvailable()) {
            LocalDate checkIn = LocalDate.parse(checkInDate);
            LocalDate checkOut = LocalDate.parse(checkOutDate);
            reservationManager.createReservation(roomNumber, guestName, checkIn, checkOut);
            roomManager.updateRoomAvailability(roomNumber, false);
            System.out.println(ConsoleColors.GREEN + "\n✓ Reservation successful!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "\n✗ Room is not available!" + ConsoleColors.RESET);
        }
    }

    private void viewReservations() {
        String guestName = getStringInput("\nEnter guest name: ");
        List<Reservation> reservations = reservationManager.getReservationsByGuestName(guestName);
        
        if (reservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo reservations found!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Your Reservations ===" + ConsoleColors.RESET);
        printReservationTable(reservations);
    }

    private void printReservationTable(List<Reservation> reservations) {
        final int ID_WIDTH = 6;
        final int ROOM_WIDTH = 7;
        final int GUEST_WIDTH = 20;
        final int DATE_WIDTH = 12;

        String headerFormat = String.format("%%-%ds %%-%ds %%-%ds %%-%ds %%-%ds",
            ID_WIDTH, ROOM_WIDTH, GUEST_WIDTH, DATE_WIDTH, DATE_WIDTH);
        String rowFormat = String.format("%%-%dd %%-%dd %%-%ds %%-%ds %%-%ds",
            ID_WIDTH, ROOM_WIDTH, GUEST_WIDTH, DATE_WIDTH, DATE_WIDTH);

        String header = String.format(headerFormat,
            "ID", "Room", "Guest", "Check-in", "Check-out");
        
        int tableWidth = header.length() + 4; // Add padding for borders
        
        // Print top border
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        
        // Print header
        System.out.println("| " + ConsoleColors.CYAN + header + ConsoleColors.RESET + " |");
        
        // Print separator
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
        
        // Print rows
        for (Reservation reservation : reservations) {
            String row = String.format(rowFormat,
                reservation.getReservationId(),
                reservation.getRoomNumber(),
                reservation.getGuestName(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
            
            System.out.println("| " + row + " |");
        }
        
        // Print bottom border
        System.out.print("+");
        for (int i = 0; i < tableWidth - 2; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }

    private void cancelReservation() {
        String guestName = getStringInput("\nEnter guest name: ");
        List<Reservation> reservations = reservationManager.getReservationsByGuestName(guestName);
        
        if (reservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW_BOLD + "\nNo reservations found!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN_BOLD + "\n=== Your Reservations ===" + ConsoleColors.RESET);
        printReservationTable(reservations);

        int reservationId = getIntInput("\nEnter reservation ID to cancel: ");
        if (reservationManager.cancelReservation(reservationId)) {
            Reservation reservation = reservationManager.getReservationById(reservationId);
            if (reservation != null) {
                roomManager.updateRoomAvailability(reservation.getRoomNumber(), true);
            }
            System.out.println(ConsoleColors.GREEN_BOLD + "\n✓ Reservation cancelled successfully!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED_BOLD + "\n✗ Reservation not found!" + ConsoleColors.RESET);
        }
    }

    private void pressEnterToContinue() {
        System.out.print(ConsoleColors.YELLOW_BOLD + "\nPress Enter to continue..." + ConsoleColors.RESET);
        scanner.nextLine();
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED_BOLD + "✗ Please enter a valid number!" + ConsoleColors.RESET);
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED_BOLD + "✗ Please enter a valid number!" + ConsoleColors.RESET);
            }
        }
    }
} 