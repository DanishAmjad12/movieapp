package com.example.daamjad.movie.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.daamjad.movie.R;
import com.example.daamjad.movie.adapters.MovieListAdapter;
import com.example.daamjad.movie.api.APIServices;
import com.example.daamjad.movie.api.Client;
import com.example.daamjad.movie.models.MovieListResponseModel;
import com.example.daamjad.movie.models.MovieModel;
import com.example.daamjad.movie.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView_movie_list)
    RecyclerView recyclerViewMovieList;

    private MovieListAdapter movieListAdapter;
    private Call<MovieModel> movieModelCall;
    private ArrayList<MovieListResponseModel> movieModelArrayList = new ArrayList<>();
    private int totalPageCount, currentPage, totalCount = 0;
    private boolean userScrolled = false;
    private LinearLayoutManager linearLayoutManagerForMovieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setupRecycleView();
        callApiForGetMovieList();
        implementScrollListener();

    }

    //region General Helper methods
    private void setupRecycleView() {

        linearLayoutManagerForMovieList = new LinearLayoutManager
                (this, LinearLayoutManager.VERTICAL, false);

        recyclerViewMovieList.setLayoutManager(linearLayoutManagerForMovieList);
    }
    //endregion

    //region Helper method for RecyclerView OnScroll

    private void implementScrollListener() {
        if (recyclerViewMovieList != null) {
            recyclerViewMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (totalCount != 1) {
                        if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                            userScrolled = true;
                        }
                    }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int visibleItemCount = linearLayoutManagerForMovieList.getChildCount();
                    int totalItemCount = linearLayoutManagerForMovieList.getItemCount();
                    int firstVisibleItemPosition = linearLayoutManagerForMovieList.
                            findFirstVisibleItemPosition();
                    if (dy > 0) {
                        if (userScrolled && firstVisibleItemPosition + visibleItemCount == totalItemCount) {
                            userScrolled = false;
                            callApiForGetMovieListForPagination();

                        }
                    }
                }
            });
        }
    }
    //endregion


    //region Helper method to get the Movie List
    private void callApiForGetMovieList() {
        if (ProjectUtils.checkIsNetworkAvailable(this)) {
            Retrofit retrofit = Client.getInstance();
            APIServices apiServices = retrofit.create(APIServices.class);
            movieModelCall = apiServices.getMovieList(getResources().getString(R.string.api_key));
            movieModelCall.enqueue(new Callback<MovieModel>() {
                @Override
                public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getMovieListResponseModelArrayList() != null) {
                                movieModelArrayList = response.body().getMovieListResponseModelArrayList();
                                totalPageCount = response.body().getTotalPages();
                                currentPage = response.body().getPage();
                                setUpAdapter();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<MovieModel> call, Throwable t) {

                }
            });
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private void callApiForGetMovieListForPagination() {
        if (ProjectUtils.checkIsNetworkAvailable(this)) {
            if (totalPageCount > currentPage) {
                currentPage++;
                Retrofit retrofit = Client.getInstance();
                APIServices apiServices = retrofit.create(APIServices.class);
                movieModelCall = apiServices.getMovieListForPagination(
                        getResources().getString(R.string.api_key), currentPage);
                movieModelCall.enqueue(new Callback<MovieModel>() {
                    @Override
                    public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMovieListResponseModelArrayList() != null) {
                                    movieModelArrayList.addAll(response.body().
                                            getMovieListResponseModelArrayList());
                                    movieListAdapter.notifyDataSetChanged();
                                    totalPageCount = response.body().getTotalPages();
                                    currentPage = response.body().getPage();
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieModel> call, Throwable t) {

                    }
                });

            }
        } else {
            Toast.makeText(this, R.string.no_internet, Toast.LENGTH_SHORT).show();
        }
    }


    //region Helper method to set the adapter
    private void setUpAdapter() {
        movieListAdapter = new MovieListAdapter(this, movieModelArrayList);
        recyclerViewMovieList.setAdapter(movieListAdapter);
    }
    //endregion

    //region Helper method for onClickListener
    @OnClick(R.id.btn_sort)
    public void sortByDateClickListener() {
        sortByDate();
        movieListAdapter.notifyDataSetChanged();
    }
    //endregion

    //region Helper method for Filters
    public void sortByDate() {
        Collections.sort(movieModelArrayList, new Comparator<MovieListResponseModel>() {
            public int compare(MovieListResponseModel objectFirst,
                               MovieListResponseModel objectSecond) {
                if (TextUtils.isEmpty(objectFirst.getReleaseDate()) ||
                        TextUtils.isEmpty(objectSecond.getReleaseDate()))
                    return 0;
                return objectFirst.getReleaseDate().compareTo(objectSecond.getReleaseDate());
            }
        });
    }
    //endregion


}
