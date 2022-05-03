package com.example.finnkino;

import java.util.ArrayList;

public class Rating extends Movie {
    private int stars;
    private String ratingText;

    public Rating(String name, String image, int stars, String ratingText, ArrayList<Rating> ratingArrayList) {
        super(name, image);
        this.stars = stars;
        this.ratingText = ratingText;
    }


    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public String getRatingText() {
        return ratingText;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }
}
