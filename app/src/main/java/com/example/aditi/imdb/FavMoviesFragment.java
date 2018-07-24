package com.example.aditi.imdb;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;


public class FavMoviesFragment extends android.support.v4.app.Fragment {


    ArrayList<Movie> movies;
    RecyclerView recyclerView;
    MovieAdapter2 adapter;

    public FavMoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {

        super.onStart();
        Log.i("OnStart","Called");
        movies = Favorite.loadFavMovies(getContext());
        adapter.notifyDataSetChanged();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_fav_movies, container, false);

        recyclerView=view.findViewById(R.id.fav_movies_recycler_view);



        movies = Favorite.loadFavMovies(getContext());
        recyclerView.setVisibility(View.VISIBLE);

        adapter= new MovieAdapter2(getContext(),movies);
        recyclerView.setAdapter(adapter);
        GridLayoutManager  manager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);




        return view;
    }



}
