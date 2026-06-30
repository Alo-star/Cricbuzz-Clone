package com.training.alokdemoapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.training.alokdemoapplication.database.entity.Favorite;
import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    long insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

    @Query("SELECT * FROM favorites WHERE type = :type ORDER BY addedAt DESC")
    List<Favorite> getFavoritesByType(String type);

    @Query("SELECT * FROM favorites ORDER BY addedAt DESC")
    List<Favorite> getAllFavorites();

    @Query("SELECT * FROM favorites WHERE entityId = :entityId AND type = :type LIMIT 1")
    Favorite getFavorite(String entityId, String type);

    @Query("DELETE FROM favorites WHERE entityId = :entityId AND type = :type")
    void removeFavorite(String entityId, String type);

    @Query("SELECT COUNT(*) FROM favorites WHERE type = 'match'")
    int getFavoriteMatchCount();
}
