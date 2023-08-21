package com.example.a1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieNameListContainer {
    private @SerializedName("results") ArrayList<MovieNameList> movieNameListArrayList;
    public ArrayList<MovieNameList> getMovieNameListArrayList() {
        return movieNameListArrayList;
    }

    @Override
    public String toString() {
        return "MovieNameListContainer{" +
                "movieNameListArrayList=" + movieNameListArrayList +
                '}';
    }
}

