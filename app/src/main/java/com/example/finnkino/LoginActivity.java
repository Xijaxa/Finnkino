package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private UsersCheck uC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.userNam);
        password = (EditText) findViewById(R.id.salas);
        Button createButton = (Button) findViewById(R.id.btnCreate);
        Button loginButton = (Button) findViewById(R.id.btnLogin);
        uC = new UsersCheck(this);


        //log in button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUP = uC.checkUsernamePassword(user, pass);
                    if(checkUP == true){
                        Toast.makeText(LoginActivity.this, "Log in successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("EXTRA", user);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid username or password!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


        // Create user button
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

    }
}