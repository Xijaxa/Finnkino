package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ProfileInfoActivity extends AppCompatActivity {
    private static final String FILE_NAME12 = "users_Info.json";
    private String username;
    Button btn;
    TextView userName, lastName, email, profileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Intent intent = getIntent();
        username = intent.getStringExtra("userName2");
        profileName = findViewById(R.id.profileName);
        userName = findViewById(R.id.userName);
        lastName = findViewById(R.id.lastname);
        email = findViewById(R.id.emailField);
        btn = findViewById(R.id.buttonpr);

        if (readJson() != null) {
            for (int i = 0; i < readJson().length; i++) {
                if(readJson()[i] !=  null) {
                    String userName1 = readJson()[i].getUserName();
                    if(userName1 != null) {
                        if (userName1.equals(username)) {

                            userName.setText(readJson()[i].getUserName());

                            lastName.setText(readJson()[i].getLastname());

                            email.setText(readJson()[i].getEmail());

                            profileName.setText(readJson()[i].getProfileName());

                        }
                    }
                }

            }

        }
    }


    public User[] readJson(){
        User[] user = null;
        try {
            // create Gson instance
            Gson gson = new Gson();

            // create a reader
            FileInputStream fis = openFileInput(FILE_NAME12);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bff = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while ((text = bff.readLine()) != null) {
                sb.append(text);
            }
            user = gson.fromJson(sb.toString(), User[].class);
            //System.out.println("##########"+user[2].getName()+"####################");

            //edT.setText(user[2].getName());
            // convert JSON file to map
            //User kk = gson.fromJson(String.valueOf(jreader), User.class);
            //System.out.println(kk.getName());
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            user = null;
            ex.printStackTrace();

        }
        return user;
    }
}