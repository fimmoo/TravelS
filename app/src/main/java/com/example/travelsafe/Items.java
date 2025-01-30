package com.example.travelsafe;

import java.io.Serializable;

public class Items implements Serializable {
    private String unid; // Add this field for unique product ID
    private String titleroom;
    private String descroom;
    private String rentroom;

    public Items() {
    }

    public Items(String unid, String titleroom, String descroom, String rentroom) {
        this.unid = unid;
        this.titleroom = titleroom;
        this.descroom = descroom;
        this.rentroom = rentroom;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getTitleroom() {
        return titleroom;
    }

    public void setTitleroom(String titleroom) {
        this.titleroom = titleroom;
    }

    public String getDescroom() {
        return descroom;
    }

    public void setDescroom(String descroom) {
        this.descroom = descroom;
    }

    public String getRentroom() {
        return rentroom;
    }

    public void setRentroom(String rentroom) {
        this.rentroom = rentroom;
    }
}