package org.example.model;

import java.time.LocalDate;

public class Reservation {
    private int reservationId;
    private int roomNumber;
    private String guestName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean isCancelled;

    public Reservation(int reservationId, int roomNumber, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = reservationId;
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isCancelled = false;
    }

    public int getReservationId() {
        return reservationId;
    }

    public int getRoomNumber() {
        return roomNumber;
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

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }
} 