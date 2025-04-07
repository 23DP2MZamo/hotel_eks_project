package org.example;

public class Room {

        private int id;
        private int floor;
        private RoomType type;
        private int beds;
        private RoomStatus status;
        private float price;

    public int getId() {
        return id;
    }
    public Object getFloor() {
        return null;
    }

    public Object getType() {
    }

    public Object getBeds() {
    }

    public Object getStatus() {
    }

    public Object getPrice() {
    }

    public enum RoomType {
            SINGLE, DOUBLE, SUITE
        }
        public enum RoomStatus {
            AVAILABLE, BOOKED
        }

        public Room(int id, int floor, RoomType type, int beds, RoomStatus status, float price) {
            this.id = id;
            this.floor = floor;
            this.type = type;
            this.beds = beds;
            this.status = status;
            this.price = price;
        }
    }