package com.example.msi.movies.main_activity.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.msi.movies.R;
import com.example.msi.movies.main_activity.MainContract;




public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    // Presenter that handles logic and data
    private MainContract.MainPresenter mPresenter;

    public MoviesAdapter(MainContract.MainPresenter presenter) {
        this.mPresenter = presenter;
    }

    public class MovieHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView img;

        public MovieHolder(View v) {
            super(v);
            title = v.findViewById(R.id.title_movie);
            img = v.findViewById(R.id.img_movie);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.onClickRecyclerItem(v, getLayoutPosition());
                }
            });
        }

        public void setTitle(String t){
            title.setText(t);
        }

        public ImageView getImg(){
            return img;
        }

    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_list_item, viewGroup, false);

        return new MovieHolder(v);
    }


    @Override
    public void onBindViewHolder(MovieHolder movieHolder, int i) {
        mPresenter.onBindMovieAtPosition(i, movieHolder);
    }

    @Override
    public int getItemCount() {
        return mPresenter.getMoviesCount();
    }


}
