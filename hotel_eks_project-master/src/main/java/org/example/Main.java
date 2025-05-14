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
        ReservationManager reservationManager = new ReservationManager();

        
        roomManager.getAllRooms().addAll(databaseManager.loadRooms());
        reservationManager.getAllReservations().addAll(databaseManager.loadReservations());

        
        ConsoleUI consoleUI = new ConsoleUI(roomManager, reservationManager);
        consoleUI.start();

        
        databaseManager.saveRooms(roomManager.getAllRooms());
        databaseManager.saveReservations(reservationManager.getAllReservations());
    }
}
