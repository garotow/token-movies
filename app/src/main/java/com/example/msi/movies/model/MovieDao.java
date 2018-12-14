package com.example.msi.movies.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie WHERE id = :id")
    Movie getMovieById(String id);

    @Insert
    void insert(Movie movie);

    @Update
    void update(Movie movie);

}
