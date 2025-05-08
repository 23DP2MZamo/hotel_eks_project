package org.example;

import org.example.model.Room;
import org.example.service.RoomManager;
import org.example.service.ReservationManager;
import org.example.ui.ConsoleUI;
import org.example.database.DatabaseManager;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        RoomManager roomManager = new RoomManager();
        ReservationManager reservationManager = new ReservationManager();

        // Load data
        roomManager.getAllRooms().addAll(databaseManager.loadRooms());
        reservationManager.getAllReservations().addAll(databaseManager.loadReservations());

        // Start the application
        ConsoleUI consoleUI = new ConsoleUI(roomManager, reservationManager);
        consoleUI.start();

        // Save data before exit
        databaseManager.saveRooms(roomManager.getAllRooms());
        databaseManager.saveReservations(reservationManager.getAllReservations());
    }
} 