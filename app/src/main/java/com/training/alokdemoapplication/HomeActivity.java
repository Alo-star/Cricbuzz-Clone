package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNav);

        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Already on Home, nothing to navigate to.
                return true;
            } else if (id == R.id.nav_live) {
                startActivity(new Intent(HomeActivity.this, LivematchActivity.class));
                return true;
            } else if (id == R.id.nav_upcoming) {
                startActivity(new Intent(HomeActivity.this, UpcomingMatchActivity.class));
                return true;
            } else if (id == R.id.nav_news) {
                Toast.makeText(HomeActivity.this, "News section coming soon", Toast.LENGTH_SHORT).show();
                return true;
            } else if (id == R.id.nav_favorites) {
                startActivity(new Intent(HomeActivity.this, FavoritesActivity.class));
                return true;
            }

            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reset selection to Home whenever the user comes back from Live/Upcoming,
        // so the bottom nav doesn't get stuck highlighting a tab that navigated away.
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}