package lv.rvt.hotel.service;

import lv.rvt.hotel.model.Reservation;
import java.util.ArrayList;
import java.util.List;

public class ReservationManager {
    private List<Reservation> reservations;
    private int nextReservationId;

    public ReservationManager() {
        this.reservations = new ArrayList<>();
        this.nextReservationId = 1;
    }

    public Reservation createReservation(int roomNumber, String guestName, String checkInDate, String checkOutDate) {
        Reservation reservation = new Reservation(nextReservationId++, roomNumber, guestName, checkInDate, checkOutDate);
        reservations.add(reservation);
        return reservation;
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

    public boolean cancelReservation(int reservationId) {
        return reservations.removeIf(r -> r.getReservationId() == reservationId);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    public Reservation getReservationById(int reservationId) {
        for (Reservation reservation : reservations) {
            if (reservation.getReservationId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }
} 