package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SigninActivity extends AppCompatActivity {
   Button registermain;
   EditText name;
   EditText mail;
   EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        name = findViewById(R.id.editTextText);
        mail = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        registermain = findViewById(R.id.register);


        registermain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name1 = name.getText().toString();
                        String mail1 = mail.getText().toString();
                        String password1 = password.getText().toString();

                        if(name1.isEmpty() || mail1.isEmpty() || password1.isEmpty()) {
                            Toast.makeText(SigninActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent i = new Intent(SigninActivity.this,MainActivity.class);
                            startActivity(i);
                        }
                    }
                }
        );



    }
}
