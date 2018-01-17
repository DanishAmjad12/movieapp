package com.example.daamjad.movie.api;


import com.example.daamjad.movie.models.MovieModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by daamjad on 1/17/2018.
 */

public interface APIServices {


    //region Helper method to get the Movie List

    @GET("movie/popular")
    Call<MovieModel> getMovieList(@Query("api_key")String apikey);
    //endregion

    //region Helper method to get the Movie List

    @GET("movie/popular")
    Call<MovieModel> getMovieListForPagination(@Query("api_key")String apikey, @Query("page") int page);
    //endregion
}
