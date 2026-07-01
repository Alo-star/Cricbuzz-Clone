package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.training.alokdemoapplication.utils.PasswordEncryption;

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

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                if (name.isEmpty()) {
                    nameField.setError("Name is required");
                    return;
                }
                if (password.isEmpty()) {
                    passwordField.setError("Password is required");
                    return;
                }

                android.content.SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
                String savedName = prefs.getString("USER_NAME", null);
                String savedEmail = prefs.getString("USER_EMAIL", null);
                String savedPassword = prefs.getString("USER_PASSWORD", null);

                if (savedName == null) {
                    Toast.makeText(MainActivity.this, "No account found. Please sign up first.", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean usernameMatches = name.equalsIgnoreCase(savedName) || name.equalsIgnoreCase(savedEmail);
                boolean passwordMatches = savedPassword != null
                        && PasswordEncryption.verifyPassword(password, savedPassword);

                if (usernameMatches && passwordMatches) {
                    Intent i = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SigninActivity.class);
                startActivity(i);
            }
        });
    }
}