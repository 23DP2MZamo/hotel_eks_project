package org.example;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvRoomSeeder {
    private static final String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/rooms.csv";

    public static void main(String[] args) {
        System.out.println("Creating rooms.csv at: " + FILE_PATH);

        File file = new File(FILE_PATH);
        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,floor,type,beds,status,price,guestName\n");

            for (int i = 1; i <= 30; i++) {
                int floor = (i - 1) / 10 + 1;
                writer.write(String.format("%d,%d,SINGLE,1,AVAILABLE,20,\n", i, floor));
            }

            for (int i = 31; i <= 50; i++) {
                int floor = (i - 31) / 10 + 4;
                writer.write(String.format("%d,%d,DUO,2,AVAILABLE,30,\n", i, floor));
            }

            for (int i = 51; i <= 100; i++) {
                int floor = (i - 51) / 10 + 6;
                writer.write(String.format("%d,%d,LUX,3,AVAILABLE,40,\n", i, floor));
            }

            writer.write("101,11,PRESIDENT,5,AVAILABLE,1000,\n");

            System.out.println("rooms.csv successfully created with 101 rooms!");
        } catch (IOException e) {
            System.err.println("Error while creating rooms.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }
}