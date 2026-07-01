package com.training.alokdemoapplication.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_matches")
public class FavoriteMatchEntity {
    @PrimaryKey
    @NonNull
    public String matchId;

    public String title;
    public long addedAt;

    public FavoriteMatchEntity() {}

    @Ignore
    public FavoriteMatchEntity(@NonNull String matchId, String title, long addedAt) {
        this.matchId = matchId;
        this.title = title;
        this.addedAt = addedAt;
    }
}
