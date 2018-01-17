package com.example.daamjad.movie.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by daamjad on 1/17/2018.
 */

public class MovieListResponseModel implements Parcelable{

    @SerializedName("id")
    private int id;

    @SerializedName("original_title")
    private String title;

    @SerializedName("original_language")
    private String movieLanguage;

    @SerializedName("poster_path")
    private String movieThumbnail;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private float rating;

    @SerializedName("overview")
    private String overView;


    protected MovieListResponseModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        movieLanguage = in.readString();
        movieThumbnail = in.readString();
        releaseDate = in.readString();
        rating = in.readFloat();
        overView = in.readString();
    }

    public static final Creator<MovieListResponseModel> CREATOR = new Creator<MovieListResponseModel>() {
        @Override
        public MovieListResponseModel createFromParcel(Parcel in) {
            return new MovieListResponseModel(in);
        }

        @Override
        public MovieListResponseModel[] newArray(int size) {
            return new MovieListResponseModel[size];
        }
    };

    //region Helper methods for Getter/Setter
    public String getOverView() {
        return overView;
    }

    public void setOverView(String overView) {
        this.overView = overView;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;

    }


    public String getTitle() {
        return title;

    }


    public void setTitle(String title) {
        this.title = title;

    }


    public String getMovieLanguage() {
        return movieLanguage;

    }


    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;

    }


    public String getMovieThumbnail() {
        return movieThumbnail;

    }


    public void setMovieThumbnail(String movieThumbnail) {
        this.movieThumbnail = movieThumbnail;

    }


    public String getReleaseDate() {
        return releaseDate;

    }


    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;

    }


    public float getRating() {
        return rating;

    }


    public void setRating(float rating) {
        this.rating = rating;

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(movieLanguage);
        parcel.writeString(movieThumbnail);
        parcel.writeString(releaseDate);
        parcel.writeFloat(rating);
        parcel.writeString(overView);
    }

    //endregion
}
