package com.example.msi.movies.main_activity;

import android.content.Context;
import android.view.View;

import com.example.msi.movies.main_activity.adapter.MoviesAdapter;

public interface MainContract {

    interface MainView {

        void showProgressBar();
        void hideProgressBar();
        void showError();
        void hideError();
        void displayToast(String msg);
        void startMovieActivity(String id);
        void enableSwipeUp();
        void disableSwipeUp();
        void notifyDataChange();
        boolean isPortrait();
        boolean isNetworkConnected();
        Context getAppContext();

    }


    interface MainPresenter {

        void onClickRecyclerItem(View v, int pos);
        void onRefresh();
        void loadMovieCatalog();
        void onBindMovieAtPosition(int position, MoviesAdapter.MovieHolder holder);
        int getMoviesCount();
        int getRecyclerColumnsNumber();
        void clearCache();


    }



}
