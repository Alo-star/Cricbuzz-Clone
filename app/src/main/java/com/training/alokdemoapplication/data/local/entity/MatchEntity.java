package com.training.alokdemoapplication.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** Cached representation of a match so the app has data to show when offline. */
@Entity(tableName = "matches")
public class MatchEntity {

    @PrimaryKey
    @NonNull
    public String matchId;

    public String title;
    public String team1Name;
    public String team1Score;
    public String team2Name;
    public String team2Score;
    public String status;
    public String startDate;
    public String venue;

    /** "LIVE" or "UPCOMING" — lets one table back both screens. */
    public String type;

    public long cachedAt;

    public MatchEntity() {}
}
