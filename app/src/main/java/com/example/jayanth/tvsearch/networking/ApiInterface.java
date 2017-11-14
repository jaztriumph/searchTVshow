package com.example.jayanth.tvsearch.networking;

import com.example.jayanth.tvsearch.models.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by jayanth on 07-11-2017.
 */

public interface ApiInterface {
    @GET("/3/search/tv?api_key=876ba1f8052dc2ee75cb845c11e9e531&language=en-US")
    Call<Movie> getTv(@Query("query") String movie, @Query("page") int page);

    @GET("/3/tv/popular?api_key=876ba1f8052dc2ee75cb845c11e9e531&language=en-US")
    Call<Movie> getPopularTv(@Query("page") int page);
//    &query={movie}&page=1
}
