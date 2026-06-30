package com.training.alokdemoapplication.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class Favorite {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type; // "match", "team", "player"
    public String entityId; // Match ID, Team name, or Player ID
    public String entityName;
    public long addedAt;

    public Favorite() {}

    public Favorite(String type, String entityId, String entityName) {
        this.type = type;
        this.entityId = entityId;
        this.entityName = entityName;
        this.addedAt = System.currentTimeMillis();
    }
}
