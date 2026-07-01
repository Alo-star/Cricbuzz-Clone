package com.training.alokdemoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Quick access cards
        MaterialCardView cardLive = findViewById(R.id.cardLive);
        MaterialCardView cardUpcoming = findViewById(R.id.cardUpcoming);
        MaterialCardView cardFavorites = findViewById(R.id.cardFavorites);
        MaterialCardView cardNews = findViewById(R.id.cardNews);

        cardLive.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, LivematchActivity.class)));

        cardUpcoming.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, UpcomingMatchActivity.class)));

        cardFavorites.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, FavoritesActivity.class)));

        cardNews.setOnClickListener(v ->
                Toast.makeText(HomeActivity.this, "News section coming soon", Toast.LENGTH_SHORT).show());

        // Bottom navigation
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
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
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }
}
