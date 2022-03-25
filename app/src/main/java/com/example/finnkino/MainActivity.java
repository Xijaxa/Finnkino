package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class MainActivity extends AppCompatActivity {


    String chosenDate;
    String timeBefore;
    String timeAfter;
    Spinner spinnerTheater;
    ListView listViewMovie;
    EditText editDate;
    EditText editAfter;
    EditText editBefore;
    ArrayList<MovieTheater> theaterList;

    SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
    SimpleDateFormat formatter2= new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    SimpleDateFormat formatter3= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    Date date = new Date(System.currentTimeMillis());
    Date dateNow = new Date(System.currentTimeMillis());
    Date date2;
    Date dateBefore;
    Date dateAfter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MovieTheaters movieTheaters = MovieTheaters.getInstance();
        theaterList = movieTheaters.getTheaterList();

        editDate = (EditText) findViewById(R.id.editTextDate);
        editAfter = (EditText) findViewById(R.id.editTextStartAfter);
        editBefore = (EditText) findViewById(R.id.editTextStartBefore);
        spinnerTheater = (Spinner) findViewById(R.id.spinner);
        listViewMovie = (ListView) findViewById(R.id.ListView);

        populateSpinnerTheater(spinnerTheater);
        populateMovieList("1029", formatter.format(date));


        spinnerTheater.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                MovieTheater theater = (MovieTheater) adapterView.getSelectedItem();
                chosenDate = editDate.getText().toString();
                    try {
                        date = formatter.parse(chosenDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        date = dateNow;
                    }
                    populateMovieList(theater.getID(), formatter.format(date));

                }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    private void populateSpinnerTheater(Spinner spinner) {
        ArrayAdapter<MovieTheater> locationAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, theaterList);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(locationAdapter);
    }


    private void populateMovieList(String ID, String date) {
        ArrayList<String> movieList = new ArrayList<String>();
        Date date3 = null;
        try {
            date3 = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            dateBefore = formatter2.parse(formatter.format(date3).toString() + " " + editBefore.getText().toString());
            timeBefore = dateBefore.toString();

        } catch (ParseException e) {
            e.printStackTrace();
            timeBefore = "";
        }
        try {
            dateAfter = formatter2.parse(formatter.format(date3).toString() + " " + editAfter.getText().toString());
            timeAfter = dateAfter.toString();

        } catch (ParseException e) {
            e.printStackTrace();
            timeAfter = "";
        }
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + ID +  "&dt=" + date;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");


            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String title = element.getElementsByTagName("Title").item(0).getTextContent();
                    String theatre = element.getElementsByTagName("TheatreAndAuditorium").item(0).getTextContent();
                    String showDate = element.getElementsByTagName("dttmShowStart").item(0).getTextContent();
                    showDate = showDate.replace("T", " ");
                    date2 = formatter3.parse(showDate);

                    if (timeAfter.equals("") && timeBefore.equals("")) {
                        movieList.add("Movie: " + title + "\nTime: " + formatter2.format(date2) + "\nPlace: " + theatre );
                    } else {
                        if ((date2.after(dateAfter) || timeAfter.equals("")) && (date2.before(dateBefore) || timeBefore.equals("")) ) {
                            movieList.add("Movie: " + title + "\nTime: " + formatter2.format(date2) + "\nPlace: " + theatre);
                        }
                    }
                }
            }
            ArrayAdapter<String> movieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, movieList);
            movieAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
            listViewMovie.setAdapter(movieAdapter);


            listViewMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String value = movieAdapter.getItem(position);
                    Toast.makeText(getApplicationContext(),value,Toast.LENGTH_LONG).show();

                }
            });

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

}

