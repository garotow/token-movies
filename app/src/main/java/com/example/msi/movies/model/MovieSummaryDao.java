package com.example.msi.movies.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface MovieSummaryDao {

    @Query("SELECT * FROM movieSummary")
    List<MovieSummary> getMovieSummary();

    @Insert
    void insert(List<MovieSummary> movies);

}
