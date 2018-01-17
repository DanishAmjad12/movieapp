package com.example.daamjad.movie.api;

import com.example.daamjad.movie.utils.WebUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by daamjad on 1/17/2018.
 */

public class Client
{

    private static Retrofit INSTANCE;


    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            setupRestClient();
        }
        return INSTANCE;
    }

    public static void setupRestClient() {

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build();

        INSTANCE = new Retrofit.Builder()
                .baseUrl(WebUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();
    }
}
