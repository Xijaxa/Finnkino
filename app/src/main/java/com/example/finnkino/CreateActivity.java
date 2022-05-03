package com.example.finnkino;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finnkino.Classes.UsersCheck;

// class for creating accounts

public class CreateActivity extends AppCompatActivity {

    private EditText username, password, repassword;
    private UsersCheck uChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        username = findViewById(R.id.userNam1);
        password = findViewById(R.id.psw1);
        repassword = findViewById(R.id.repsw);
        Button cancel = findViewById(R.id.btnCancel);
        Button register = findViewById(R.id.btnRegister);
        uChk = new UsersCheck(this);

        // Checks that user doesn't already exist and that password meets the requirements
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();
                CharacterChecker checker;
                if(user.equals("") || pass.equals("") || repass.equals("")){
                    Toast.makeText(CreateActivity.this, "please enter fields",Toast.LENGTH_SHORT).show();
                }else{
                    if(pass.equals(repass)){
                        Boolean checkUsername = uChk.checkUsername(user);
                        if(checkUsername == false){
                            checker = new CharacterChecker(pass);
                            if(pass.length()>= 12 && checker.Uppercase() && checker.Lowercase() && checker.numbers() &&
                                    checker.specialCharacters()) {
                                Boolean insert = uChk.insertData(user, pass);

                                if(insert == true){
                                    String uName = username.getText().toString();
                                    Toast.makeText(CreateActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                    intent.putExtra("EXTRA", uName);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(CreateActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(CreateActivity.this, "Your password does not meet the requirements of a safe password please enter new.", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(CreateActivity.this, "User already exists! Please sign up.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(CreateActivity.this, "Passwords not matching!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Returns to login screen
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
