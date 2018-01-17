package com.example.daamjad.movie.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.daamjad.movie.R;
import com.example.daamjad.movie.models.MovieListResponseModel;
import com.example.daamjad.movie.utils.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by daamjad on 1/17/2018.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.title)
    AppCompatTextView textViewTitle;

    @BindView(R.id.over_view)
    AppCompatTextView textViewOverView;

    @BindView(R.id.release_date)
    AppCompatTextView textViewReleaseDate;

    @BindView(R.id.image_poster)
    AppCompatImageView imageViewPoster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ButterKnife.bind(this);

        getBundleData();

    }

    //region Helper method to get the Bundle item
    private void getBundleData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            MovieListResponseModel movieListResponseModel = bundle.getParcelable(
                    BundleConstants.MOVIE_MODEL);
            if (movieListResponseModel != null)
                setViewsData(movieListResponseModel);
        }
    }
    //endregion

    //region Helper method for setViews Data
    private void setViewsData(MovieListResponseModel responseModel) {

        if (responseModel != null) {
            textViewTitle.setText(String.format("Title: %s", responseModel.getTitle()));
            textViewReleaseDate.setText(String.format("Release Date: %s", responseModel.getReleaseDate()));
            textViewOverView.setText(String.format("Description: %s", responseModel.getOverView()));
            setPosterImage(responseModel.getMovieThumbnail());
        }

    }
    //endregion

    private void setPosterImage(String url) {

        Glide.with(this).load(WebUtil.IMAGE_BASE_URL + url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transform(new CenterCrop(imageViewPoster.getContext()))
                .into(imageViewPoster);
    }

}
