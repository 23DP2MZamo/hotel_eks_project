package lv.rvt.hotel;

import lv.rvt.hotel.database.CsvRoomSeeder;
import lv.rvt.hotel.database.DatabaseManager;
import lv.rvt.hotel.service.RoomManager;
import lv.rvt.hotel.service.ReservationManager;
import lv.rvt.hotel.ui.ConsoleUI;
import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (!new File("rooms.csv").exists()) {
            System.out.println("Ielādē sākotnējos datus...");
            CsvRoomSeeder.seedRooms();
        }

        DatabaseManager dbManager = new DatabaseManager();
        RoomManager roomManager = new RoomManager();
        ReservationManager reservationManager = new ReservationManager();

        roomManager.getAllRooms().addAll(dbManager.loadRooms());
        reservationManager.getAllReservations().addAll(dbManager.loadReservations());

        ConsoleUI ui = new ConsoleUI(roomManager, reservationManager);
        ui.start();
    }
} 