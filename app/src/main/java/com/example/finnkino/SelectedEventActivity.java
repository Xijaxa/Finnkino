package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.finnkino.Classes.Event;

import java.util.ArrayList;

// Populates the list with selected event lists and also removes them when clicked
public class SelectedEventActivity extends AppCompatActivity {

    private ArrayList<Event> selectedEventList;
    private ListView listViewEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_events);
        selectedEventList =  (ArrayList<Event>) getIntent().getSerializableExtra("eventsMenu2");
        listViewEvent = findViewById(R.id.ListView);
        populateSelectedEventList(selectedEventList);
    }

    public void loadActivity (View v) {
        Intent intent = new Intent(SelectedEventActivity.this, MenuActivity.class);
        intent.putExtra("eventsEvents", selectedEventList);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void populateSelectedEventList(ArrayList<Event> selectedEventList) {
        ArrayAdapter<Event> movieAdapter = new ArrayAdapter<Event>(this,
                R.layout.list_yellow_text, selectedEventList);
        movieAdapter.setDropDownViewResource(R.layout.list_yellow_text);
        listViewEvent.setAdapter(movieAdapter);

        listViewEvent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                        long l) {
                    Event event1 = movieAdapter.getItem(position);
                    String msg = "Movie: " + event1.getName() + "\nTime: " + event1.getTime()
                            + "\nLocation: " + event1.getLocation() + "\n Removed from events!";
                    Toast.makeText(getApplicationContext(),msg ,Toast.LENGTH_LONG).show();
                    selectedEventList.remove(position);
                    populateSelectedEventList(selectedEventList);
            }
        });

    }
}