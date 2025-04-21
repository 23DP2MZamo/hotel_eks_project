package org.example;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager dbManager = new DatabaseManager();
        RoomManager roomManager = new RoomManager(dbManager);

        System.out.print("Введите ваше имя: ");
        String userName = scanner.nextLine();

        while (true) {
            System.out.println("\n--- Меню ---");
            System.out.println("1. Show available rooms: ");
            System.out.println("2. Book a room: ");
            System.out.println("3. Close system.");
            System.out.println("4. Show my bookings");
            System.out.println("5. Cancel my bookings.");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    roomManager.showAvailableRooms();
                    break;
                case "2":
                    System.out.print("Type ID(number) for a room you wanna book: ");
                    int roomId = Integer.parseInt(scanner.nextLine());
                    roomManager.bookRoom(roomId, userName);
                    break;
                case "3":
                    System.out.println("Goodbye, thank you for using my system, " + userName + "!");
                    return;
                case "4":
                    roomManager.findReservationsByName(userName);
                    break;
                case "5":
                    roomManager.cancelReservationByName(userName);
                    break;
                default:
                    System.out.println("Wrong choice.");

            }
        }
    }
}