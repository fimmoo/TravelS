package com.example.travelsafe;

public class RoomDetais {
    private String roomtitle;
    private String roomdescription;
    private String rent;


    public RoomDetais(String roomtitle, String description, String rent, String uniqueId) {
    }

    public RoomDetais(String roomtitle, String roomdescription, String rent) {
        this.roomtitle = roomtitle;
        this.roomdescription = roomdescription;
        this.rent = rent;

    }

    public String getRoomtitle() {
        return roomtitle;
    }

    public void setRoomtitle(String roomtitle) {
        this.roomtitle = roomtitle;
    }

    public String getRoomdescription() {
        return roomdescription;
    }

    public void setRoomdescription(String roomdescription) {
        this.roomdescription = roomdescription;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

}