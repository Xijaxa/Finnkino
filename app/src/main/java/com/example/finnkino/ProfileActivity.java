package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.finnkino.Classes.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {
    private static final String FILE_NAME123 = "users_Info.json";
    ArrayList<User> users1 = new ArrayList<>();
    Button btn;
    EditText name, lastname, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent1 = getIntent();
        String username1 = intent1.getStringExtra("EXTRA");
        System.out.println("##########"+username1+"#############");
        name = findViewById(R.id.name);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        btn = findViewById(R.id.buttonpr);
        if(readJson() != null){
            users1.clear();
            for(int i =0; i<readJson().length; i++){
                users1.add(readJson()[i]);
            }
        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(name.getText().toString(), lastname.getText().toString(),
                        email.getText().toString(), username1);


                users1.add(user);
                File path = getApplicationContext().getFilesDir();

                try{
                    FileOutputStream out = openFileOutput(FILE_NAME123, MODE_PRIVATE);
                    Gson gson = new Gson();         //(PrintWriter out = new PrintWriter(new FileWriter(path)))
                    String json = gson.toJson(users1);
                    out.write(json.getBytes(StandardCharsets.UTF_8));
                    out.close();
                }catch(Exception e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("EXTRA", username1);
                startActivity(intent);
            }
        });
    }

    public User[] readJson(){
        User[] user = null;
        try {
            // create Gson instance
            Gson gson = new Gson();
            // create a reader
            FileInputStream fis = openFileInput(FILE_NAME123);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bff = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;
            while((text = bff.readLine()) != null){
                sb.append(text);
            }
            user = gson.fromJson(sb.toString(), User[].class);
        } catch (Exception ex) {
            user = null;
            ex.printStackTrace();
        }
        return user;
    }
}
