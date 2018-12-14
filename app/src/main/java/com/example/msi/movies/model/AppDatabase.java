package com.example.msi.movies.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class, MovieSummary.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public final static String ROOM_DB_NAME = "movies_database";

    public abstract MovieSummaryDao movieSummaryDao();
    public abstract MovieDao movieDao();

    private static AppDatabase INSTANCE;

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                     Room.databaseBuilder(context,
                            AppDatabase.class, ROOM_DB_NAME)
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }
}
