package org.example;

public class Room {

    private int id;
    private int floor;
    private RoomType type;
    private int beds;
    private RoomStatus status;
    private float price;

    public enum RoomType {
        SINGLE,
        DUO,
        LUX,
        PRESIDENT
    }

    public enum RoomStatus {
        AVAILABLE, BOOKED
    }

    private String guestName; // Добавлено

    public Room(int id, int floor, RoomType type, int beds, RoomStatus status, float price, String guestName) {
        this.id = id;
        this.floor = floor;
        this.type = type;
        this.beds = beds;
        this.status = status;
        this.price = price;
        this.guestName = guestName;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public int getId() {
        return id;
    }

    public int getFloor() {
        return floor;
    }

    public RoomType getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public float getPrice() {
        return price;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
            String description = switch (type) {
                case SINGLE -> "Breakfast included.";
                case DUO -> "Breakfast and dinner included.";
                case LUX -> "Access to the sauna and swimming pools.";
                case PRESIDENT -> "Access to sauna and pools.Cleaning, 3 meals a day, sauna, pool, highest room.";
            };
        return description;
    }

    }