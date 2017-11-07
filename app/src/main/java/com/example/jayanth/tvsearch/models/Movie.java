package com.example.jayanth.tvsearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jayanth on 07-11-2017.
 */


public class Movie {
    @SerializedName("name")
    String name;
    @SerializedName("original_name")
    String originalName;
}
