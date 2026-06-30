package com.training.alokdemoapplication.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.training.alokdemoapplication.database.dao.UserDao;
import com.training.alokdemoapplication.database.dao.MatchDao;
import com.training.alokdemoapplication.database.dao.FavoriteDao;
import com.training.alokdemoapplication.database.entity.User;
import com.training.alokdemoapplication.database.entity.Match;
import com.training.alokdemoapplication.database.entity.Favorite;

@Database(
    entities = {User.class, Match.class, Favorite.class},
    version = 1,
    exportSchema = true
)
public abstract class CricbuzzDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract MatchDao matchDao();
    public abstract FavoriteDao favoriteDao();

    private static volatile CricbuzzDatabase INSTANCE;

    public static CricbuzzDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (CricbuzzDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CricbuzzDatabase.class,
                            "cricbuzz_database"
                    )
                    .fallbackToDestructiveMigration()
                    .build();
                }
            }
        }
        return INSTANCE;
    }
}
