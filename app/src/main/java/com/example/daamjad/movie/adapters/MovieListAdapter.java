package com.example.daamjad.movie.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.daamjad.movie.R;
import com.example.daamjad.movie.models.MovieListResponseModel;
import com.example.daamjad.movie.ui.BundleConstants;
import com.example.daamjad.movie.ui.DetailActivity;
import com.example.daamjad.movie.utils.WebUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by daamjad on 1/17/2018.
 */

public class MovieListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<MovieListResponseModel> movieListResponseModels;


    public MovieListAdapter(Activity activity, ArrayList<MovieListResponseModel> mMovieList) {
        this.mContext = activity;
        this.movieListResponseModels = mMovieList;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item,
                parent, false);

        return new MovieListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MovieListResponseModel item = getValueAt(position);
        MovieListAdapter.MovieListViewHolder mostPopularViewHolder = (MovieListAdapter.MovieListViewHolder) holder;
        if (item != null) {
            setupValuesInWidgets(mostPopularViewHolder, item);
        }
    }


    private MovieListResponseModel getValueAt(int position) {
        return movieListResponseModels.get(position);
    }

    @Override
    public int getItemCount() {
        return movieListResponseModels.size();
    }

    private void setupValuesInWidgets(MovieListViewHolder itemHolder, MovieListResponseModel
            movieListResponseModel) {
        if (movieListResponseModel != null) {

            itemHolder.textViewTitle.setText(String.format("Title: %s", movieListResponseModel.getTitle()));
            itemHolder.textViewReleaseDate.setText(String.format("Release Date %s", movieListResponseModel.getReleaseDate()));
            itemHolder.setPosterImage(WebUtil.IMAGE_BASE_URL + movieListResponseModel.getMovieThumbnail());
        }
    }


    public class MovieListViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.title)
        AppCompatTextView textViewTitle;

        @BindView(R.id.release_date)
        AppCompatTextView textViewReleaseDate;

        @BindView(R.id.image_poster)
        AppCompatImageView imageViewPoster;


        public MovieListViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        @OnClick(R.id.linear_layout_parent)
        public void onItemClick() {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(mContext, DetailActivity.class);
            bundle.putParcelable(BundleConstants.MOVIE_MODEL, movieListResponseModels.get(getAdapterPosition()));
            intent.putExtras(bundle);
            mContext.startActivity(intent);
        }

        void setPosterImage(String url) {

            Glide.with(mContext).load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(new CenterCrop(imageViewPoster.getContext()))
                    .into(imageViewPoster);
        }

    }

}
