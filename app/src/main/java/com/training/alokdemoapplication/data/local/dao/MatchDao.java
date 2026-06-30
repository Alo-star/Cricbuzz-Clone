package com.training.alokdemoapplication.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.training.alokdemoapplication.data.local.entity.MatchEntity;

import java.util.List;

@Dao
public interface MatchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<MatchEntity> matches);

    @Query("SELECT * FROM matches WHERE type = :type ORDER BY cachedAt DESC")
    LiveData<List<MatchEntity>> observeByType(String type);

    @Query("SELECT * FROM matches WHERE type = :type ORDER BY cachedAt DESC")
    List<MatchEntity> getByType(String type);

    @Query("DELETE FROM matches WHERE type = :type")
    void clearType(String type);
}
