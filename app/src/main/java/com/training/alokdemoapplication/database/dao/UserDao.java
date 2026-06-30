package com.training.alokdemoapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.training.alokdemoapplication.database.entity.User;

@Dao
public interface UserDao {
    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE name = :name LIMIT 1")
    User getUserByName(String name);

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    User getLoggedInUser();

    @Update
    void updateUser(User user);

    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUser(int userId);

    @Query("UPDATE users SET isLoggedIn = 0")
    void logoutAllUsers();
}
