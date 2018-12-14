package com.example.msi.movies.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieAPI {

    String BASE_URL = "https://desafio-mobile.nyc3.digitaloceanspaces.com/";

    @GET("movies")
    Call<List<MovieSummary>> fetchMoviesSummary();


    @GET("movies/{id}")
    Call<Movie> fetchMovieById(@Path("id") String id);
}
