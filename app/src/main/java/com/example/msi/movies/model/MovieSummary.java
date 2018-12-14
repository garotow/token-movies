package com.example.msi.movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class MovieSummary {

    @NonNull
    @PrimaryKey
    private String id;
    private String title;
    private String poster_url;

    @NonNull
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_url() {
        return poster_url;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoster_url(String poster_url) {
        this.poster_url = poster_url;
    }
}
