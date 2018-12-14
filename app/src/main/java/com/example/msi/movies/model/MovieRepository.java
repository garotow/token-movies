package com.example.msi.movies.model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {

    public final static String TAG = "MovieRepository";

    private Context mContext;
    private MovieRepoListener mListener;
    private Retrofit retrofit;
    private MovieAPI api;
    private static AppDatabase appDatabase;

    public interface MovieRepoListener {
        void onReceiveMovieSummary(List<MovieSummary> movies);

        void onReceiveMovie(Movie movie);

        void onFailure(Throwable t);
    }


    public MovieRepository(MovieRepoListener l, Context ctx) {
        mListener = l;
        mContext = ctx;
        retrofit = new Retrofit.Builder()
                .baseUrl(MovieAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MovieAPI.class);

        appDatabase = AppDatabase.getAppDatabase(ctx);
    }

    public void updateMovie(Movie movie){
        appDatabase.movieDao().update(movie);
    }

    public void requestMovieById(String id) {
        // Try to get Movie from local database
        Movie movie = appDatabase.movieDao().getMovieById(id);

        // If data is cached, return it and don't need to make a network request
        if (movie != null) {
            mListener.onReceiveMovie(movie);
            Log.i(TAG, "Movie " + id + " on local storage!");
            return;
        }

        // Request Movie Details from TokenLab API
        Call<Movie> call = api.fetchMovieById(id);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    appDatabase.movieDao().insert(response.body());
                    mListener.onReceiveMovie(response.body());
                } else
                    mListener.onFailure(new Throwable());
                Log.i(TAG, Integer.toString(response.code()));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                mListener.onFailure(t);
            }
        });
    }

    public void requestMoviesSummary() {
        // Try to get Movies from local database
        List<MovieSummary> movies = appDatabase.movieSummaryDao().getMovieSummary();

        // If data is cached, return it and don't need to make a network request
        if (!movies.isEmpty()) {
            mListener.onReceiveMovieSummary(movies);
            return;
        }

        // Request MovieSummary list from TokenLab API
        Call<List<MovieSummary>> call = api.fetchMoviesSummary();
        call.enqueue(new Callback<List<MovieSummary>>() {
            @Override
            public void onResponse(Call<List<MovieSummary>> call, Response<List<MovieSummary>> response) {
                if (response.isSuccessful()) {
                    appDatabase.movieSummaryDao().insert(response.body());
                    mListener.onReceiveMovieSummary(response.body());
                } else
                    mListener.onFailure(new Throwable());
                Log.i(TAG, Integer.toString(response.code()));
            }

            @Override
            public void onFailure(Call<List<MovieSummary>> call, Throwable t) {
                mListener.onFailure(t);
            }
        });

    }

    public void clearAllTables(){
        appDatabase.clearAllTables();
        Log.i(TAG, "cleared tables");
    }

    public boolean isStored(String id){
        Movie movie = appDatabase.movieDao().getMovieById(id);
        return (movie != null);
    }
}
