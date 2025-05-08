package lv.rvt.hotel.database;

import lv.rvt.hotel.model.Room;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvRoomSeeder {
    private static final String ROOMS_FILE = "rooms.csv";

    public static void seedRooms() {
        List<Room> rooms = new ArrayList<>();
        
        // Add some sample rooms
        rooms.add(new Room(101, "Standard", 100.0));
        rooms.add(new Room(102, "Standard", 100.0));
        rooms.add(new Room(201, "Deluxe", 150.0));
        rooms.add(new Room(202, "Deluxe", 150.0));
        rooms.add(new Room(301, "Suite", 250.0));
        rooms.add(new Room(302, "Suite", 250.0));

        try (PrintWriter writer = new PrintWriter(ROOMS_FILE)) {
            for (Room room : rooms) {
                writer.printf("%d,%s,%.2f,%b\n",
                    room.getRoomNumber(),
                    room.getType(),
                    room.getPrice(),
                    room.isAvailable());
            }
            System.out.println("Istabu dati veiksmīgi ielādēti!");
        } catch (Exception e) {
            System.err.println("Kļūda ielādējot istabu datus: " + e.getMessage());
        }
    }
} 