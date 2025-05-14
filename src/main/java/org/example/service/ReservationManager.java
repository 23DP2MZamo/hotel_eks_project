package org.example.service;

import org.example.model.Reservation;
import org.example.database.DatabaseManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private List<Reservation> reservations;
    private int nextReservationId;
    private final DatabaseManager databaseManager;

    public ReservationManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.reservations = new ArrayList<>();
        this.nextReservationId = 1;
    }

    public Reservation createReservation(int roomNumber, String guestName, LocalDate checkInDate, LocalDate checkOutDate) {
        Reservation reservation = new Reservation(nextReservationId++, roomNumber, guestName, checkInDate, checkOutDate);
        reservations.add(reservation);
        saveReservations();
        return reservation;
    }

    public boolean cancelReservation(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                reservation.setCancelled(true);
                saveReservations();
                return true;
            }
        }
        return false;
    }

    public List<Reservation> getReservationsByGuestName(String guestName) {
        List<Reservation> guestReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getGuestName().equals(guestName)) {
                guestReservations.add(reservation);
            }
        }
        return guestReservations;
    }

    public Reservation getReservationById(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    public int getActiveReservationCount() {
        return (int) reservations.stream()
                .filter(reservation -> !reservation.isCancelled())
                .count();
    }

    public int getCancelledReservationCount() {
        return (int) reservations.stream()
                .filter(Reservation::isCancelled)
                .count();
    }

    public int getTotalReservationCount() {
        return reservations.size();
    }

    public void saveReservations() {
        databaseManager.saveReservations(reservations);
    }

    public void loadReservations() {
        reservations.clear();
        reservations.addAll(databaseManager.loadReservations());
        // Update nextReservationId based on existing reservations
        nextReservationId = reservations.stream()
                .mapToInt(Reservation::getReservationId)
                .max()
                .orElse(0) + 1;
    }
} 