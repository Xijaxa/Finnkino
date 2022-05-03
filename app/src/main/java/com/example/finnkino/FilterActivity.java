package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.finnkino.Classes.Filter;
import com.example.finnkino.Classes.Movie;
import com.example.finnkino.Classes.MovieTheater;
import com.example.finnkino.Classes.MovieTheaters;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FilterActivity extends AppCompatActivity {

    private Spinner spinnerTheater;
    private Spinner spinnerMovie;
    private Button buttonDate;
    private Button buttonAfter;
    private Button buttonBefore;
    private ArrayList<MovieTheater> theaterList;
    private ArrayList<Movie> movieList;
    private DatePickerDialog datePickerDialog;
    private Filter filters;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        MovieTheaters movieTheaters = MovieTheaters.getInstance();
        theaterList = movieTheaters.getTheaterList();
        movieList = new ArrayList<>();
        filters = new Filter();
        filters = (Filter) getIntent().getSerializableExtra("filters");
        buttonDate = findViewById(R.id.buttonDate);
        buttonAfter = findViewById(R.id.buttonAfter);
        buttonBefore = findViewById(R.id.buttonBefore);
        spinnerTheater = (Spinner) findViewById(R.id.spinner);
        spinnerMovie = (Spinner) findViewById(R.id.spinner2);
        // Option for any movie
        Movie noMovie = new Movie("Valitse elokuva", "");
        movieList.add(noMovie);
        createMovieList();
        populateSpinnerTheater(spinnerTheater);
        populateSpinnerMovie(spinnerMovie);
        initDatePicker();
        // Setting previously used values when opening filters again
        buttonDate.setText(filters.getDate());
        spinnerTheater.setSelection(filters.getTheaterPosition());
        spinnerMovie.setSelection(filters.getMoviePosition());
        buttonAfter.setText(filters.getTimeAfter());
        buttonBefore.setText(filters.getTimeBefore());

    }



    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        month = month + 1;
        return makeDateString(year, month, day);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                buttonDate.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, dateSetListener, day, month, year);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private String makeDateString (int day, int month, int year) {
        // Make string with format dd.mm.yyyy
        String monthString;
        String dayString;
        if (month < 10){
            monthString = "0" + month;  //make all months 02 digits
        } else {
            monthString = "" + month;
        }
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = "" + day;
        }

        String date = year + "." + monthString + "." + dayString;
        return date;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void resetFilters (View v) {
        spinnerTheater.setSelection(0);
        spinnerMovie.setSelection(0);
        buttonDate.setText(getTodaysDate());
        buttonAfter.setText(String.format(Locale.getDefault(), "%02d:%02d", 00, 00));
        buttonBefore.setText(String.format(Locale.getDefault(), "%02d:%02d", 23, 59));
    }

    public void loadActivity (View v) {
        Intent intent = new Intent(FilterActivity.this, MainActivity.class);
        // Get current values for filters
        MovieTheater theater = (MovieTheater) spinnerTheater.getSelectedItem();
        Movie movie = (Movie) spinnerMovie.getSelectedItem();
        String chosenDate = buttonDate.getText().toString();
        String timeBefore = buttonBefore.getText().toString();
        String timeAfter = buttonAfter.getText().toString();
        if(chosenDate.length() < 10) {
            chosenDate = "0" + chosenDate;
        }
        // Put filter values into object for
        filters.setTheaterPosition(spinnerTheater.getSelectedItemPosition());
        filters.setMoviePosition(spinnerMovie.getSelectedItemPosition());
        filters.setTheater(theater.getID());
        filters.setDate(chosenDate);
        filters.setMovie(movie.getName());
        filters.setTimeAfter(timeAfter);
        filters.setTimeBefore(timeBefore);
        intent.putExtra("filters", filters);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void populateSpinnerTheater(Spinner spinner) {
        SpinnerTheaterAdapter customAdapter = new SpinnerTheaterAdapter(this,
                R.layout.custom_spinner_adapter, theaterList);
        spinner.setAdapter(customAdapter);
    }

    private void populateSpinnerMovie(Spinner spinner) {
    SpinnerMovieAdapter customAdapter = new SpinnerMovieAdapter(this,
            R.layout.custom_spinner_adapter, movieList);
    spinner.setAdapter(customAdapter);
    }


    private void createMovieList() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/";
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String title = element.getElementsByTagName("Title").item(0)
                            .getTextContent();
                    String image = element.getElementsByTagName("EventSmallImagePortrait")
                            .item(0).getTextContent();
                    if(movieList.stream().anyMatch(o -> o.getName().equals(title))) {

                    } else {
                        Movie movie = new Movie(title, image);
                        movieList.add(movie);
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

          // Time filter for earliest time
    public void popTimePickerAfter(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.
                OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                buttonAfter.setText(String.format(Locale.getDefault(), "%02d:%02d",
                        hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,
                hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }
         // Time picker for latest time
    public void popTimePickerBefore(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new
                TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                buttonBefore.setText(String.format(Locale.getDefault(), "%02d:%02d",
                        hour, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                onTimeSetListener, hour, minute, true);
        timePickerDialog.setTitle("Select time");
        timePickerDialog.show();
    }
}