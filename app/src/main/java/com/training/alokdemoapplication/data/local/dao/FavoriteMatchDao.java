package com.training.alokdemoapplication.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.training.alokdemoapplication.data.local.entity.FavoriteMatchEntity;

import java.util.List;

@Dao
public interface FavoriteMatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteMatchEntity match);

    @Delete
    void delete(FavoriteMatchEntity match);

    @Query("SELECT * FROM favorite_matches ORDER BY addedAt DESC")
    LiveData<List<FavoriteMatchEntity>> observeAll();

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_matches WHERE matchId = :matchId)")
    boolean isFavorite(String matchId);
}
