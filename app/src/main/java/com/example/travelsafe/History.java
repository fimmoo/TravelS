package com.example.travelsafe;

public class History {
    private String roomname;
    private String checkin;
    private String checkout;
    private String reservationName;


    public History() {
    }

    public History(String roomname, String checkin, String checkout, String reservationName, String uid) {
        this.roomname = roomname;
        this.checkin = checkin;
        this.checkout = checkout;
        this.reservationName = reservationName;

    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) { // Fixed: Added parameter
        this.roomname = roomname;
    }

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) { // Fixed: Added parameter
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) { // Fixed: Added parameter
        this.checkout = checkout;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

}