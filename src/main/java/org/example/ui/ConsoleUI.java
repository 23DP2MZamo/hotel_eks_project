package lv.rvt.hotel.ui;

import lv.rvt.hotel.model.Room;
import lv.rvt.hotel.model.Reservation;
import lv.rvt.hotel.service.RoomManager;
import lv.rvt.hotel.service.ReservationManager;
import lv.rvt.hotel.util.ConsoleColors;
import java.util.List;
import java.util.Scanner;

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
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Izvēlieties opciju: ");
            running = processChoice(choice);
        }
    }

    private void displayMenu() {
        System.out.println(ConsoleColors.CYAN + "\n=== Viesnīcas Rezervāciju Sistēma ===" + ConsoleColors.RESET);
        System.out.println("1. Rezervēt istabu");
        System.out.println("2. Parādīt nerezervētas istabas");
        System.out.println("3. Aizvērt programmu");
        System.out.println("4. Parādīt jūsu rezervētas istabas");
        System.out.println("5. Atcelt rezervējumu");
    }

    private boolean processChoice(int choice) {
        switch (choice) {
            case 1:
                makeReservation();
                break;
            case 2:
                showAvailableRooms();
                break;
            case 3:
                return false;
            case 4:
                showUserReservations();
                break;
            case 5:
                cancelReservation();
                break;
            default:
                System.out.println(ConsoleColors.RED + "Nederīga izvēle!" + ConsoleColors.RESET);
        }
        return true;
    }

    private void makeReservation() {
        showAvailableRooms();
        int roomNumber = getIntInput("Ievadiet istabas numuru: ");
        String guestName = getStringInput("Ievadiet viesu vārdu: ");
        String checkInDate = getStringInput("Ievadiet ierašanās datumu (YYYY-MM-DD): ");
        String checkOutDate = getStringInput("Ievadiet izbraukšanas datumu (YYYY-MM-DD): ");

        Room room = roomManager.getRoomByNumber(roomNumber);
        if (room != null && room.isAvailable()) {
            Reservation reservation = reservationManager.createReservation(roomNumber, guestName, checkInDate, checkOutDate);
            roomManager.updateRoomAvailability(roomNumber, false);
            System.out.println(ConsoleColors.GREEN + "Rezervācija veikta veiksmīgi!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Istaba nav pieejama!" + ConsoleColors.RESET);
        }
    }

    private void showAvailableRooms() {
        List<Room> availableRooms = roomManager.getAvailableRooms();
        if (availableRooms.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nav pieejamu istabu!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN + "\nPieejamās istabas:" + ConsoleColors.RESET);
        for (Room room : availableRooms) {
            System.out.printf("Istaba %d - %s - %.2f EUR\n", 
                room.getRoomNumber(), room.getType(), room.getPrice());
        }
    }

    private void showUserReservations() {
        String guestName = getStringInput("Ievadiet viesu vārdu: ");
        List<Reservation> reservations = reservationManager.getReservationsByGuestName(guestName);
        
        if (reservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nav atrastu rezervāciju!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN + "\nJūsu rezervācijas:" + ConsoleColors.RESET);
        for (Reservation reservation : reservations) {
            System.out.printf("Rezervācija #%d - Istaba %d - %s līdz %s\n",
                reservation.getReservationId(),
                reservation.getRoomNumber(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
        }
    }

    private void cancelReservation() {
        String guestName = getStringInput("Ievadiet viesu vārdu: ");
        List<Reservation> reservations = reservationManager.getReservationsByGuestName(guestName);
        
        if (reservations.isEmpty()) {
            System.out.println(ConsoleColors.YELLOW + "Nav atrastu rezervāciju!" + ConsoleColors.RESET);
            return;
        }

        System.out.println(ConsoleColors.CYAN + "\nJūsu rezervācijas:" + ConsoleColors.RESET);
        for (Reservation reservation : reservations) {
            System.out.printf("Rezervācija #%d - Istaba %d - %s līdz %s\n",
                reservation.getReservationId(),
                reservation.getRoomNumber(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate());
        }

        int reservationId = getIntInput("Ievadiet rezervācijas numuru, kuru vēlaties atcelt: ");
        if (reservationManager.cancelReservation(reservationId)) {
            Reservation reservation = reservationManager.getReservationById(reservationId);
            if (reservation != null) {
                roomManager.updateRoomAvailability(reservation.getRoomNumber(), true);
            }
            System.out.println(ConsoleColors.GREEN + "Rezervācija atcelta veiksmīgi!" + ConsoleColors.RESET);
        } else {
            System.out.println(ConsoleColors.RED + "Rezervācija nav atrasta!" + ConsoleColors.RESET);
        }
    }

    private int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(ConsoleColors.RED + "Lūdzu ievadiet skaitli!" + ConsoleColors.RESET);
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
} 