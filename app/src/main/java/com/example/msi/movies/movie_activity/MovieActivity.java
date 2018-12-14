package com.example.msi.movies.movie_activity;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.msi.movies.R;
import com.example.msi.movies.main_activity.MainActivity;


public class MovieActivity extends AppCompatActivity implements MovieContract.MovieView {

    /* ID of current Movie */
    private String movieId;

    /* Layout Android Views */
    private TextView mOverview;
    private TextView mTitle;
    private TextView mReleaseDate;
    private TextView mVoteAverage;
    private ImageView mBackdrop;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;
    private FloatingActionButton mFab;

    private CollapsingToolbarLayout mCollapsingToolBar;

    private MovieContract.MoviePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        // Get 'movieId' passed from MainActivity
        movieId = getIntent().getExtras().getString(MainActivity.EXTRA_MOVIE_ID);

        initViews();
        mPresenter.loadMovieDetails(movieId);
    }


    void initViews(){
        mPresenter = new MoviePresenter(this);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Layout Views
        mTitle = findViewById(R.id.movie_title);
        mProgressBar = findViewById(R.id.progressBar);
        mOverview = findViewById(R.id.movie_overview);
        mReleaseDate = findViewById(R.id.movie_release);
        mVoteAverage = findViewById(R.id.movie_vote);
        mCollapsingToolBar = findViewById(R.id.toolbar_layout);
        mBackdrop = findViewById(R.id.img_backdrop);
        mCoordinatorLayout = findViewById(R.id.cordLayout);
        mCollapsingToolBar.setTitle("");
        mFab = findViewById(R.id.fab);
        // Floating Action Button

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onClickFavoriteButton(view);
            }
        });
    }

    public void displayDetails(String title, String overview, String vote, String release){
        mTitle.setText(title);
        mCollapsingToolBar.setTitle(title);
        mOverview.setText(overview);
        mVoteAverage.setText(vote);
        mReleaseDate.setText(release);
    }


    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(this).setTitle("Oooopss....")
                .setMessage("No internet connection.")
                .setNeutralButton("Ok.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showSanck(String msg){
        Snackbar snackbar = Snackbar.make(mCoordinatorLayout, msg, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public ImageView getBackdropImage() {
        return mBackdrop;
    }

    @Override
    public void setFabIcon(boolean isfav) {
        if (isfav)
            mFab.setImageResource(R.drawable.heart);
        else
            mFab.setImageResource(R.drawable.heartoutline);

        // Avoid Icon Disappear bug
        mFab.hide();
        mFab.show();
    }

    @Override
    public void setFabVisible(boolean visible){
        if (visible)
            mFab.show();
        else
            mFab.hide();
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }
}
