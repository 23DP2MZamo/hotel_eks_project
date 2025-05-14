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
}
