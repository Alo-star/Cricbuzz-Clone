package com.training.alokdemoapplication.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "matches")
public class Match {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String matchId; // Unique match ID from API
    public String team1;
    public String team2;
    public String team1Score;
    public String team2Score;
    public String status;
    public String venue;
    public String matchDate;
    public String format; // T20, ODI, Test
    public long viewedAt; // When user last viewed this match
    public boolean isFavorite;

    public Match() {}

    public Match(String matchId, String team1, String team2, String venue, String matchDate, String format) {
        this.matchId = matchId;
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.matchDate = matchDate;
        this.format = format;
        this.team1Score = "-";
        this.team2Score = "-";
        this.status = "Upcoming";
        this.viewedAt = System.currentTimeMillis();
        this.isFavorite = false;
    }
}
