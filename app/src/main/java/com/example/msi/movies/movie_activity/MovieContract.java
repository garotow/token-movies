package com.example.msi.movies.movie_activity;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

public interface MovieContract {

    interface MovieView{

        void showProgress();

        void hideProgress();

        void showError();

        void showSanck(String msg);

        void displayDetails(String title, String overview, String vote, String release);

        ImageView getBackdropImage();

        void setFabIcon(boolean isfav);

        void setFabVisible(boolean visible);

        Context getAppContext();




    }

    interface MoviePresenter{

        void onClickFavoriteButton(View v);
        void loadMovieDetails(String id);

    }


}
