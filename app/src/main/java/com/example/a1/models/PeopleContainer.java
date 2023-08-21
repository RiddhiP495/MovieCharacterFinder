package com.example.a1.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PeopleContainer {
    private @SerializedName("results") ArrayList<CharacterName> characterNameArrayList;

    public ArrayList<CharacterName> getCharacterNameArrayList() {
        return characterNameArrayList;
    }

    @Override
    public String toString() {
        return "PeopleContainer{" +
                "characterNameArrayList=" + characterNameArrayList +
                '}';
    }
}

