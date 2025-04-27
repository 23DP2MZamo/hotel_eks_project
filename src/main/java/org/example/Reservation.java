package org.example;

import java.time.LocalDate;

public class Reservation {
    private int roomId;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isActive;

    public Reservation(int roomId, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        this.roomId = roomId;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isActive = true;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public String toString() {
        return String.format("Room ID: %d | Guest: %s | Check-in: %s | Check-out: %s | Status: %s",
                roomId, guestName, checkInDate, checkOutDate, isActive ? "Active" : "Cancelled");
    }
} 