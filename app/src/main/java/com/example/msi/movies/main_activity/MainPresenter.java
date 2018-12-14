package com.example.msi.movies.main_activity;

import android.view.View;

import com.example.msi.movies.R;
import com.example.msi.movies.main_activity.adapter.MoviesAdapter;
import com.example.msi.movies.model.Movie;
import com.example.msi.movies.model.MovieRepository;
import com.example.msi.movies.model.MovieSummary;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.MainPresenter, MovieRepository.MovieRepoListener {

    private MainContract.MainView mView;
    private MovieRepository movieRepo;
    private List<MovieSummary> movieCatalog;


    public MainPresenter(MainContract.MainView view){
        mView = view;
        movieCatalog = new ArrayList<>();
        movieRepo = new MovieRepository(this, mView.getAppContext());
    }

    /* ------- MainActivity  -------  */
    @Override
    public void onRefresh() {
        loadMovieCatalog();
    }

    @Override
    public void loadMovieCatalog() {
        mView.hideError();
        mView.disableSwipeUp();
        mView.showProgressBar();
        movieRepo.requestMoviesSummary();
    }

    @Override
    public int getRecyclerColumnsNumber() {
        if (mView.isPortrait())
            return 2;
        else
            return 3;
    }


    /* ------- RecyclerView Adapter  -------  */
    @Override
    public void onClickRecyclerItem(View v, int position) {
        mView.startMovieActivity(movieCatalog.get(position).getId());
    }

    @Override
    public int getMoviesCount() {
        return movieCatalog.size();
    }

    @Override
    public void onBindMovieAtPosition(int position, MoviesAdapter.MovieHolder holder) {
        holder.setTitle(movieCatalog.get(position).getTitle());
        Picasso.get().load(movieCatalog.get(position)
                .getPoster_url())
                .error( R.drawable.nomovie )
                .placeholder( R.drawable.loading_animation )
                .into(holder.getImg());
    }



    /* ------- Callbacks from Repository -------  */
    @Override
    public void onReceiveMovieSummary(List<MovieSummary> movies) {
        movieCatalog = movies;
        mView.hideProgressBar();
        mView.disableSwipeUp();
        mView.notifyDataChange();
    }

    @Override
    public void onReceiveMovie(Movie movie) {
        // Not using
    }

    @Override
    public void onFailure(Throwable t) {
        mView.hideProgressBar();
        mView.enableSwipeUp();
        mView.showError();
    }


}
