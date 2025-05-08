package lv.rvt.hotel.model;

public class Reservation {
    private int reservationId;
    private int roomNumber;
    private String guestName;
    private String checkInDate;
    private String checkOutDate;

    public Reservation(int reservationId, int roomNumber, String guestName, String checkInDate, String checkOutDate) {
        this.reservationId = reservationId;
        this.roomNumber = roomNumber;
        this.guestName = guestName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
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

    public String getCheckInDate() {
        return checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }
} 