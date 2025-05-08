package org.example.model;

public class Room {
    private int roomNumber;
    private int floor;
    private String type;
    private int beds;
    private double price;
    private boolean available;
    private String guestName;

    public Room(int roomNumber, int floor, String type, int beds, double price) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.type = type;
        this.beds = beds;
        this.price = price;
        this.available = true;
        this.guestName = null;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }
} 