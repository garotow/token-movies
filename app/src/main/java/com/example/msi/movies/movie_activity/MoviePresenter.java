package com.example.msi.movies.movie_activity;

import android.view.View;


import com.example.msi.movies.model.Movie;
import com.example.msi.movies.model.MovieRepository;
import com.example.msi.movies.model.MovieSummary;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePresenter implements MovieContract.MoviePresenter, MovieRepository.MovieRepoListener {

    private MovieContract.MovieView mView;
    private MovieRepository movieRepo;
    private Movie movie;

    public MoviePresenter(MovieContract.MovieView view) {
        this.mView = view;
        movie = null;
        movieRepo = new MovieRepository(this, mView.getAppContext());
    }

    @Override
    public void loadMovieDetails(String id){
        mView.showProgress();
        movieRepo.requestMovieById(id);
    }

    @Override
    public void onClickFavoriteButton(View v) {
        if (movie == null)
            return;

        if (movie.isFavorite()){
            movie.setFavorite(false);
            mView.showSanck("Removido dos favoritos!");
            mView.setFabIcon(false);
        }
        else {
            movie.setFavorite(true);
            mView.showSanck("Adicionado aos favoritos!");
            mView.setFabIcon(true);
        }
        // Update favorite state on database
        movieRepo.updateMovie(movie);
    }


    /* ------- Callbacks from Repository -------  */
    @Override
    public void onReceiveMovieSummary(List<MovieSummary> movies) {
        // Not using
    }

    @Override
    public void onReceiveMovie(Movie movie) {
        this.movie = movie;
        mView.hideProgress();
        mView.setFabIcon(movie.isFavorite());
        mView.displayDetails(movie.getTitle(), movie.getOverview(), movie.getVote_average() + " / 10", movie.getRelease_date());
        Picasso.get().load(movie.getBackdrop_url())
                .into(mView.getBackdropImage());
    }

    @Override
    public void onFailure(Throwable t) {
        mView.showError();
    }
}
