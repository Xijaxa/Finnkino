package com.example.finnkino;

import java.io.Serializable;

public class Movie implements Serializable {
    protected final String name;
    protected final String image;

    public Movie(String name, String image) {
        this.name = name;
        this.image = image;

    }


    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
