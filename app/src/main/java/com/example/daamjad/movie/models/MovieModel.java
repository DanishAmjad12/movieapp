package com.example.daamjad.movie.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by daamjad on 1/17/2018.
 */

public class MovieModel
{
    @SerializedName("page")
    private int page;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("results")
    private ArrayList<MovieListResponseModel> movieListResponseModelArrayList=new ArrayList<>();

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<MovieListResponseModel> getMovieListResponseModelArrayList() {
        return movieListResponseModelArrayList;
    }

    public void setMovieListResponseModelArrayList(ArrayList<MovieListResponseModel> movieListResponseModelArrayList) {
        this.movieListResponseModelArrayList = movieListResponseModelArrayList;
    }
}
