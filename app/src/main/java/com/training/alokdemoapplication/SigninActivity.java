package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.training.alokdemoapplication.utils.PasswordEncryption;

public class SigninActivity extends AppCompatActivity {

    private Button registermain;
    private EditText name;
    private EditText mail;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        name = findViewById(R.id.editTextName);
        mail = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        registermain = findViewById(R.id.btnSignin);

        registermain.setOnClickListener(v -> {

            String name1 = name.getText().toString().trim();
            String mail1 = mail.getText().toString().trim();
            String password1 = password.getText().toString().trim();

            if (name1.isEmpty()) {
                name.setError("Name is required");
                return;
            }

            if (mail1.isEmpty()) {
                mail.setError("Email is required");
                return;
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail1).matches()) {
                mail.setError("Enter a valid email");
                return;
            }

            if (password1.isEmpty()) {
                password.setError("Password is required");
                return;
            }

            if (password1.length() < 4) {
                password.setError("Password must be at least 4 characters");
                return;
            }

            // Save the account locally so MainActivity can verify login.
            // Password is salted + SHA-256 hashed before storage — never store plain text.
            String hashedPassword = PasswordEncryption.hashPassword(password1);
            getSharedPreferences("user_prefs", MODE_PRIVATE)
                    .edit()
                    .putString("USER_NAME", name1)
                    .putString("USER_EMAIL", mail1)
                    .putString("USER_PASSWORD", hashedPassword)
                    .apply();

            Toast.makeText(this, "Account created! Please log in.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SigninActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}