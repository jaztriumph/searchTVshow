package com.example.jayanth.tvsearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by jayanth on 07-11-2017.
 */

public class MovieInfo {

    @SerializedName("results")
    private ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }
}
