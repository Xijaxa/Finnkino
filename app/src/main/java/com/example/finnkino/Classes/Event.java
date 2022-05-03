package com.example.finnkino.Classes;

import java.io.Serializable;

public class Event extends Movie implements Serializable {
    private final String time;
    private final String location;



    public Event(String name, String image, String time, String location) {
        super(name, image);
        this.time = time;
        this.location = location;
    }


    public String getTime() {
        return time;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
         String string = "Movie: " + name + "\nTime: " + time
                 + "\nLocation: " + location;
        return string;
    }
}
