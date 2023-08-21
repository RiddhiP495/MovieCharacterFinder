package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.a1.adapters.CharacterAdapter;
import com.example.a1.databinding.ActivityMainBinding;
import com.example.a1.models.CharacterName;
import com.example.a1.models.MovieNameListContainer;
import com.example.a1.models.PeopleContainer;
import com.example.a1.network.RetrofitClient;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, OnCharacterClickListener {

    ActivityMainBinding binding;
    private ArrayList<String> movieNameList;
    private ArrayAdapter<String> adapter;
    private String TAG = this.getClass().getCanonicalName();
    private ArrayList<CharacterName> characterNameList;
    private CharacterAdapter characterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        this.movieNameList = new ArrayList<>();
        movieNameList.add(0,"Select a Movie");
        this.adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                this.movieNameList);
        this.adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.binding.spnMovieName.setAdapter(this.adapter);
        this.getMovieName();
        this.binding.spnMovieName.setOnItemSelectedListener(this);

    }

    private void getMovieName(){
        Call<MovieNameListContainer> listContainerCall = RetrofitClient.getInstance().getApi().retrieveMovieName();
        //  Call<MovieNameListContainer> listContainerCall = RetrofitClient.getInstance().getApi().retrieveName("4");
        try{
            listContainerCall.enqueue(new Callback<MovieNameListContainer>() {

                @Override
                public void onResponse(Call<MovieNameListContainer> call, Response<MovieNameListContainer> response) {
                    if(response.code() == 200){

                        MovieNameListContainer mainResponse = response.body();
                        Log.d(TAG,"onResponse: Received Response: " + mainResponse.toString());
                        Log.d(TAG,"onResponse: Number of movies: " + mainResponse.getMovieNameListArrayList().size());
                        Log.d(TAG,"onResponse: MovieList: " + mainResponse.getMovieNameListArrayList().toString());

                        if(mainResponse.getMovieNameListArrayList().isEmpty()){
                            Log.e(TAG,"onResponse: No Movies found");

                        }else {
                            movieNameList.clear();
                            for(int i=0; i< mainResponse.getMovieNameListArrayList().size(); i++){
                                Log.d(TAG,"onResponse: MovieList object: " + mainResponse.getMovieNameListArrayList().get(i).toString());
                                movieNameList.add(mainResponse.getMovieNameListArrayList().get(i).getMovieName());
                                // movieNameList.add(String.valueOf(mainResponse.getMovieNameListArrayList().get(i).getCurrentCharacter()));
                            }

                            adapter.notifyDataSetChanged();
                        }

                    }else {
                        Log.e(TAG,"onResponse: Unsuccessful response"+ response.code() + response.errorBody());
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<MovieNameListContainer> call, Throwable t) {
                    call.cancel();;
                    Log.e(TAG,"onFailure: Failed to get the movieList from API" + t.getLocalizedMessage());
                }
            });
        }catch(Exception ex){
            Log.e(TAG,"getMovieNameList: Cannot retrieve movie name list" + ex.getLocalizedMessage());

        }
    }

    private void getCharacterName() {
        Call<PeopleContainer> peopleCall = RetrofitClient.getInstance().getApi().retrievePeopleName();
        try {
            peopleCall.enqueue(new Callback<PeopleContainer>() {
                @Override
                public void onResponse(Call<PeopleContainer> call, Response<PeopleContainer> response) {
                    if(response.code() == 200){
                        if(response.body() != null){
                            Log.d(TAG,"onResponse: "+response.body().getCharacterNameArrayList());
                            ArrayList<CharacterName> characterNames = response.body().getCharacterNameArrayList();
                            Log.d(TAG,"onResponse: Number of name and species received: " + characterNames.size());
                            characterNameList.clear();
                            characterNameList.addAll(characterNames);
                            //notify adapter for the data change
                            characterAdapter.notifyDataSetChanged();
                        }else{
                            Log.e(TAG,"onResponse: Null response received");
                        }
                    }else{
                        Log.e(TAG,"onResponse: Unsuccessful response received" + response.code());
                    }
                    call.cancel();
                }

                @Override
                public void onFailure(Call<PeopleContainer> call, Throwable t) {
                    call.cancel();
                    Log.e(TAG, "onFailure: Failed to get name and category" + t.getLocalizedMessage());
                }
            });

        } catch (Exception ex) {
            Log.e(TAG, "getRandomRecipe: Cannot retrieve name and species category " + ex.getLocalizedMessage());
        }
    }

//    private void getName(String id){
//        Call<MovieNameListContainer> listContainerCall = RetrofitClient.getInstance().getApi().retrieveName(id);
//       // Call<MovieNameListContainer> listContainerCall = RetrofitClient.getInstance().getApi().retrieveName("4");
//        try{
//            listContainerCall.enqueue(new Callback<MovieNameListContainer>() {
//
//                @Override
//                public void onResponse(Call<MovieNameListContainer> call, Response<MovieNameListContainer> response) {
//                    if(response.code() == 200){
//
//                        MovieNameListContainer mainResponse = response.body();
//                        Log.d(TAG,"onResponse: Received Response: " + mainResponse.toString());
//                        Log.d(TAG,"onResponse: Number of movies: " + mainResponse.getMovieNameListArrayList().size());
//                        Log.d(TAG,"onResponse: MovieList: " + mainResponse.getMovieNameListArrayList().toString());
//
//                        if(mainResponse.getMovieNameListArrayList().isEmpty()){
//                            Log.e(TAG,"onResponse: No Movies found");
//
//                        }else {
//                            movieNameList.clear();
//                            for(int i=0; i< mainResponse.getMovieNameListArrayList().size(); i++){
//                                Log.d(TAG,"onResponse: MovieList object: " + mainResponse.getMovieNameListArrayList().get(i).toString());
//                                //movieNameList.add(mainResponse.getMovieNameListArrayList().get(i).getMovieName());
//                                 movieNameList.add(String.valueOf(mainResponse.getMovieNameListArrayList().get(i).getCharacterNames()));
//
//                            }
//                            adapter.notifyDataSetChanged();
//                        }
//
//                    }else {
//                        Log.e(TAG,"onResponse: Unsuccessful response"+ response.code() + response.errorBody());
//                    }
//                    call.cancel();
//                }
//
//                @Override
//                public void onFailure(Call<MovieNameListContainer> call, Throwable t) {
//                    call.cancel();;
//                    Log.e(TAG,"onFailure: Failed to get the movieList from API" + t.getLocalizedMessage());
//                }
//            });
//        }catch(Exception ex){
//            Log.e(TAG,"getMovieNameList: Cannot retrieve movie name list" + ex.getLocalizedMessage());
//
//        }
//    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.characterNameList = new ArrayList<>();
        this.characterAdapter = new CharacterAdapter(this, this.characterNameList, this::onCharacterClicked);
        this.binding.rvCharacter.setLayoutManager(new LinearLayoutManager(this));
        this.binding.rvCharacter.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(), DividerItemDecoration.VERTICAL));
        this.binding.rvCharacter.setAdapter(this.characterAdapter);
        this.getCharacterName();
        Log.d(TAG,"onItemClick: Selected movie name: " + this.movieNameList.get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCharacterClicked(CharacterName names) {
        Log.d(TAG, "onCharacterName clicked: User selected" + names.toString());
        Snackbar.make(binding.getRoot(), names.getName() + " appears in "  , Snackbar.LENGTH_SHORT)
                .show();

    }
}