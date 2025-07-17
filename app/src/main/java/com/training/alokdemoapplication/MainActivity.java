package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button signin;
    EditText nameField;
    EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button);
        signin = findViewById(R.id.noaccount);
         nameField = findViewById(R.id.editTextText);
         passwordField = findViewById(R.id.password);


        b1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name = nameField.getText().toString();
                        String password = passwordField.getText().toString();
                        if(name.isEmpty() || password.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Please fill all the details", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Intent i = new Intent(MainActivity.this,SecondActivity.class);
                            startActivity(i);
                        }
                    }
                }
        );

        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(MainActivity.this,SigninActivity.class);
                        startActivity(i);
                    }
                }
        );




    }
}