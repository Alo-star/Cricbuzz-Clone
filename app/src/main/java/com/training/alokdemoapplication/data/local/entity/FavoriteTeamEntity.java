package com.training.alokdemoapplication.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_teams")
public class FavoriteTeamEntity {
    @PrimaryKey
    @NonNull
    public String teamName;

    public long addedAt;

    public FavoriteTeamEntity() {}

    @Ignore
    public FavoriteTeamEntity(@NonNull String teamName, long addedAt) {
        this.teamName = teamName;
        this.addedAt = addedAt;
    }
}
