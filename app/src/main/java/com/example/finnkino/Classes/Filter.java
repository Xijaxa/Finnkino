package com.example.finnkino.Classes;

import java.io.Serializable;

public class Filter implements Serializable {
        private String theater;
        private String movie;
        private String date;
        private String timeBefore;
        private String timeAfter;
        private int theaterPosition;
        private int moviePosition;

        public String getMovie() { return movie; }

        public void setMovie(String movie) { this.movie = movie; }

        public String getTheater() { return theater; }

        public void setTheater(String theater) { this.theater = theater; }

        public String getDate() { return date; }

        public void setDate(String date) { this.date = date; }

        public String getTimeBefore() { return timeBefore; }

        public void setTimeBefore(String timeBefore) { this.timeBefore = timeBefore; }

        public String getTimeAfter() { return timeAfter; }

        public void setTimeAfter(String timeAfter) { this.timeAfter = timeAfter; }

        public int getTheaterPosition() { return theaterPosition; }

        public void setTheaterPosition(int theaterPosition) { this.theaterPosition = theaterPosition; }

        public int getMoviePosition() { return moviePosition; }

        public void setMoviePosition(int moviePosition) { this.moviePosition = moviePosition; }
}
