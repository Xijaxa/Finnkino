package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private ArrayList<Event> selectedEventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Changing title colour to black
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"black\">" + getString(R.string.app_name) + "</font>"));
        selectedEventList = new ArrayList<>();
        selectedEventList =  (ArrayList<Event>) getIntent().getSerializableExtra("eventsHome");
    }

    public void loadHome (View v) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        intent.putExtra("eventsMenu", selectedEventList);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void loadProfile (View v) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }
    public void loadLogout (View v) {
        Intent intent = new Intent(MenuActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void loadSelectedEvents (View v) {
        Intent intent = new Intent(MenuActivity.this, SelectedEventActivity.class);
        intent.putExtra("eventsMenu2", selectedEventList);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                selectedEventList = (ArrayList<Event>) data.getSerializableExtra("eventsEvents");
            }
        }
    }



}