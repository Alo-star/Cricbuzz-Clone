package com.training.alokdemoapplication.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.training.alokdemoapplication.data.local.entity.FavoriteTeamEntity;

import java.util.List;

@Dao
public interface FavoriteTeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteTeamEntity team);

    @Delete
    void delete(FavoriteTeamEntity team);

    @Query("SELECT * FROM favorite_teams ORDER BY addedAt DESC")
    LiveData<List<FavoriteTeamEntity>> observeAll();

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_teams WHERE teamName = :teamName)")
    boolean isFavorite(String teamName);
}
