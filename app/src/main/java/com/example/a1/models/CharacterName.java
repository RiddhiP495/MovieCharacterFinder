package com.example.a1.models;

import java.util.ArrayList;

public class CharacterName {
    private String name;
    private ArrayList<String> species;
    private String url;

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getSpecies() {
        return species;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "CharacterName{" +
                "name='" + name + '\'' +
                ", species=" + species +
                ", url='" + url + '\'' +
                '}';
    }
}


