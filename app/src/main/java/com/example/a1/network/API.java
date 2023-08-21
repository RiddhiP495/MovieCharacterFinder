package com.example.a1.network;

import com.example.a1.models.MovieNameListContainer;
import com.example.a1.models.PeopleContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://swapi.dev/api/";

    @GET("./films/")
    Call<MovieNameListContainer> retrieveMovieName();

    @GET("./people/")
    Call<PeopleContainer> retrievePeopleName();

    @GET("./people/")
    Call<MovieNameListContainer> retrieveName(@Query("id") String id);


}
