package com.example.a1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieNameList {
    private @SerializedName("title") String movieName;
    private @SerializedName("character") ArrayList<CharacterName> characterNames;

    public ArrayList<CharacterName> getCharacterNames() {
        return characterNames;
    }

    public String getMovieName() {
        return movieName;
    }

    @Override
    public String toString() {
        return "MovieNameList{" +
                "movieName='" + movieName + '\'' +
                ", characterNames=" + characterNames +
                '}';
    }
}


