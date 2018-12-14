package com.example.msi.movies.main_activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.msi.movies.R;
import com.example.msi.movies.main_activity.adapter.ItemOffsetDecoration;
import com.example.msi.movies.main_activity.adapter.MoviesAdapter;
import com.example.msi.movies.movie_activity.MovieActivity;


public class MainActivity extends AppCompatActivity implements MainContract.MainView {

    public static final String EXTRA_MOVIE_ID = "com.example.msi.movies.main_activity.ID";

    /* Layout Android Views */
    private SwipeRefreshLayout mSwipeLayout;
    private ProgressBar mProgBar;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMovieAdapter;
    private ImageView mImageError;
    private GridLayoutManager mLayoutManager;

    /* MainPresenter of this View */
    private MainContract.MainPresenter mPresenter;

    /* RecyclerView position to restore */
    private static int currentVisiblePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mPresenter.loadMovieCatalog();
    }

    // Default Views Initializing
    public void initViews(){
        // Presenter of this View
        mPresenter = new MainPresenter(this);

        // Toolbar
        getSupportActionBar().setTitle("Token Movies");


        // Bind Views
        mProgBar = findViewById(R.id.progress_bar);
        mRecyclerView = findViewById(R.id.movie_list);
        mSwipeLayout = findViewById(R.id.swiperefresh);
        mImageError = findViewById(R.id.img_error);

        // Recycler View
        mMovieAdapter = new MoviesAdapter(mPresenter);
        mRecyclerView.setAdapter(mMovieAdapter);
        int columnsNumber = mPresenter.getRecyclerColumnsNumber();
        mLayoutManager = new GridLayoutManager(this, columnsNumber);
        mRecyclerView.setLayoutManager(mLayoutManager);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(this, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        // Disable Swipe Up
        mSwipeLayout.setRefreshing(false);
        mSwipeLayout.setEnabled(false);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onRefresh();
            }
        });

    }

    @Override
    public void showProgressBar() {
        mProgBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgBar.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mImageError.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyDataChange(){
        mMovieAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean isPortrait() {
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void hideError(){
        mImageError.setVisibility(View.GONE);
    }

    @Override
    public void displayToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startMovieActivity(String id) {
        Intent intent = new Intent(this, MovieActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, id);
        startActivity(intent);
    }

    @Override
    public void enableSwipeUp(){
        mSwipeLayout.setEnabled(true);
    }

    @Override
    public void disableSwipeUp(){
        mSwipeLayout.setRefreshing(false);
        mSwipeLayout.setEnabled(false);
    }

    @Override
    public Context getAppContext() {
        return getApplicationContext();
    }

    @Override
    protected void onPause() {
        super.onPause();
        currentVisiblePosition = 0; // this variable should be static in class
        currentVisiblePosition = mLayoutManager.findFirstVisibleItemPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLayoutManager.scrollToPosition(currentVisiblePosition);
        currentVisiblePosition = 0;
    }
}
