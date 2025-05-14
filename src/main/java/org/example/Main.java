package org.example;

import org.example.model.Room;
import org.example.service.RoomManager;
import org.example.service.ReservationManager;
import org.example.ui.ConsoleUI;
import org.example.database.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new DatabaseManager();
        RoomManager roomManager = new RoomManager(databaseManager);
        ReservationManager reservationManager = new ReservationManager(databaseManager);

        // Load initial data
        reservationManager.loadReservations();
        // Sync room status with loaded reservations
        roomManager.syncRoomStatusWithReservations(reservationManager.getAllReservations());

        // Start the UI
        ConsoleUI consoleUI = new ConsoleUI(roomManager, reservationManager);
        consoleUI.start();

        // Save data before exit
        databaseManager.saveRooms(roomManager.getAllRooms());
        databaseManager.saveReservations(reservationManager.getAllReservations());
    }
}
