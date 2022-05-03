package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import android.widget.Toolbar;


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

    private ListView listViewMovie;
    private ArrayList<Event> eventList;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
    private final SimpleDateFormat formatter2 = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    private final SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Date date = new Date(System.currentTimeMillis());
    private Date dateBefore;
    private Date dateAfter;
    private Filter filters;
    private ArrayList<Event> selectedEventList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Changing title colour to black
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" +
                getString(R.string.app_name) + "</font>"));

        // Making default filters
        filters = new Filter();
        filters.setDate(formatter.format(date));
        filters.setMovie("Valitse elokuva");
        filters.setTimeAfter("00:00");
        filters.setTimeBefore("23:59");
        filters.setTheater("1029");
        filters.setTheaterPosition(0);
        filters.setMoviePosition(0);

        eventList = new ArrayList<>();
        listViewMovie = findViewById(R.id.ListView);
        populateEventList("1029", formatter.format(date), eventList);

    }


    public void loadActivity (View v) {
        Intent intent = new Intent(MainActivity.this, FilterActivity.class);
        intent.putExtra("filters", filters);
        startActivityForResult(intent, 1);
    }

    public void loadMenu (View v) {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.putExtra("eventsHome", selectedEventList);
        startActivityForResult(intent, 2);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                filters = (Filter) data.getSerializableExtra("filters");
                populateEventList(filters.getTheater(), filters.getDate(), eventList);

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                selectedEventList = (ArrayList<Event>) data.getSerializableExtra("eventsMenu");
            }
        }
    }

    // Finding events using Finnkino xml data and adding it to list then displaying on listview
    // based on filters
    private void populateEventList(String ID, String date, ArrayList<Event> eventList) {
        eventList.clear();
        Event event;

        // Setup for time comparisons
        Date date3 = null;
        try {
            date3 = formatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Searching the xml data and parsing the time filters
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String urlString = "https://www.finnkino.fi/xml/Schedule/?area=" + ID +  "&dt=" + date;
            Document doc = builder.parse(urlString);
            doc.getDocumentElement().normalize();
            NodeList nodeList = doc.getDocumentElement().getElementsByTagName("Show");
            String timeBefore;
            try {
                dateBefore = formatter2.parse(formatter.format(date3) + " " +
                        filters.getTimeBefore() + ":00");
                timeBefore = dateBefore.toString();
            } catch (ParseException e) {
                e.printStackTrace();
                timeBefore = "23:59:00";
            }
            String timeAfter;
            try {
                dateAfter = formatter2.parse(formatter.format(date3) + " " +
                        filters.getTimeAfter() + ":00");
                timeAfter = dateAfter.toString();

            } catch (ParseException e) {
                e.printStackTrace();
                timeAfter = "00:00:00";
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String title = element.getElementsByTagName("Title").item(0)
                            .getTextContent();
                    String theatre = element.getElementsByTagName("TheatreAndAuditorium")
                            .item(0).getTextContent();
                    String showDate = element.getElementsByTagName("dttmShowStart").item(0)
                            .getTextContent();
                    String image = element.getElementsByTagName("EventSmallImagePortrait")
                            .item(0).getTextContent();
                    showDate = showDate.replace("T", " ");
                    Date date2 = formatter3.parse(showDate);
                    String time = formatter2.format(date2);

                    // Adding events to eventList based on filters used
                    if(filters.getMovie().equals("Valitse elokuva")) {
                       if (timeAfter.equals("00:00:00") && timeBefore.equals("23:59:00")) {
                           event = new Event(title, image, time, theatre);
                           eventList.add(event);
                       } else {
                            if ((date2.after(dateAfter) || timeAfter.equals("00:00:00")) &&
                                    (date2.before(dateBefore) || timeBefore.equals("23:59:00")) ) {
                                event = new Event(title, image, time, theatre);
                                eventList.add(event);
                            }
                        }
                    } else {
                        if (title.equals(filters.getMovie()) ) {
                                if ((date2.after(dateAfter) || timeAfter.equals("00:00:00")) &&
                                        (date2.before(dateBefore) || timeBefore.equals("23:59:00"))) {
                                    event = new Event(title, image, time, theatre);
                                    eventList.add(event);
                            }
                        }
                    }
                }
            }

            // Adding the events in eventList to listview
            ArrayAdapter<Event> movieAdapter = new ArrayAdapter<Event>(this,
                    R.layout.list_yellow_text, eventList);
            movieAdapter.setDropDownViewResource(R.layout.list_yellow_text);
            listViewMovie.setAdapter(movieAdapter);

                //Adding clicked events to event list
            listViewMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long l) {

                    Event event1 = movieAdapter.getItem(position);
                    if(selectedEventList.stream().anyMatch(o -> o.getName().equals(event1.getName()))) {
                        String msg1 = "Already added to events.";
                        Toast.makeText(getApplicationContext(),msg1 ,Toast.LENGTH_LONG).show();

                    } else {
                        String msg2 = "Movie: " + event1.getName() + "\nTime: " + event1.getTime()
                                + "\nLocation: " + event1.getLocation() + "\n Added to events.";
                        Toast.makeText(getApplicationContext(),msg2 ,Toast.LENGTH_LONG).show();
                        selectedEventList.add(event1);
                    }

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


