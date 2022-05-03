package com.example.finnkino.Classes;

public class MovieTheater {
    private final String location;
    private final String ID;

    public MovieTheater(String id, String place) {
        this.location = place;
        this.ID = id;
    }

    @Override
    public String toString() {
        return location;
    }

    public String getLocation() {
        return location;
    }

    public String getID() {
        return ID;
    }
}
