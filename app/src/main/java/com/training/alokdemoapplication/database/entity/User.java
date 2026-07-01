package com.training.alokdemoapplication.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String email;
    public String passwordHash; // Encrypted password
    public long createdAt;
    public long updatedAt;
    public boolean isLoggedIn;

    @Ignore
    public User(String name, String email, String passwordHash) {
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.isLoggedIn = false;
    }

    public User() {}
}
