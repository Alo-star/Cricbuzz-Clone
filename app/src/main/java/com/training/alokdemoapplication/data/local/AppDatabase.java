package com.training.alokdemoapplication.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.training.alokdemoapplication.data.local.dao.FavoriteMatchDao;
import com.training.alokdemoapplication.data.local.dao.FavoriteTeamDao;
import com.training.alokdemoapplication.data.local.dao.MatchDao;
import com.training.alokdemoapplication.data.local.entity.FavoriteMatchEntity;
import com.training.alokdemoapplication.data.local.entity.FavoriteTeamEntity;
import com.training.alokdemoapplication.data.local.entity.MatchEntity;

@Database(
        entities = {MatchEntity.class, FavoriteTeamEntity.class, FavoriteMatchEntity.class},
        version = 1,
        exportSchema = true
)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MatchDao matchDao();
    public abstract FavoriteTeamDao favoriteTeamDao();
    public abstract FavoriteMatchDao favoriteMatchDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "cricbuzz_clone.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
