package com.training.alokdemoapplication.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.training.alokdemoapplication.database.entity.Match;
import java.util.List;

@Dao
public interface MatchDao {
    @Insert
    long insertMatch(Match match);

    @Query("SELECT * FROM matches WHERE matchId = :matchId LIMIT 1")
    Match getMatchById(String matchId);

    @Query("SELECT * FROM matches ORDER BY matchDate DESC")
    List<Match> getAllMatches();

    @Query("SELECT * FROM matches WHERE status = 'Live' ORDER BY matchDate DESC")
    List<Match> getLiveMatches();

    @Query("SELECT * FROM matches WHERE status = 'Upcoming' ORDER BY matchDate ASC")
    List<Match> getUpcomingMatches();

    @Query("SELECT * FROM matches WHERE isFavorite = 1 ORDER BY matchDate DESC")
    List<Match> getFavoriteMatches();

    @Update
    void updateMatch(Match match);

    @Query("DELETE FROM matches WHERE matchId = :matchId")
    void deleteMatch(String matchId);

    @Query("UPDATE matches SET viewedAt = :timestamp WHERE matchId = :matchId")
    void updateViewedTime(String matchId, long timestamp);
}
