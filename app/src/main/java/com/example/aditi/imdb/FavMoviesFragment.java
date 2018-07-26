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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;


public class FavMoviesFragment extends android.support.v4.app.Fragment {


    ArrayList<Movie> movies;
    RecyclerView recyclerView;
    MovieAdapter2 adapter;
    FrameLayout frameLayout;
    LottieAnimationView animationView;

    public FavMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View view= inflater.inflate(R.layout.fragment_fav_movies, container, false);
        frameLayout=view.findViewById(R.id.fragment_favMovies_rootLayout);
        recyclerView=view.findViewById(R.id.fav_movies_recycler_view);

        movies=new ArrayList<>();
        adapter = new MovieAdapter2(getContext(), movies);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);


        ArrayList<Movie> movies1 = Favorite.loadFavMovies(getContext());
        for(int i=0;i<movies1.size();i++){
            movies.add(movies1.get(i));
        }

        if(!movies1.isEmpty()) {
            if(frameLayout.indexOfChild(animationView)>-1) {
                frameLayout.removeView(animationView);
            }
            adapter.notifyDataSetChanged();
            recyclerView.setVisibility(View.VISIBLE);

        }
        else{
            animationView=new LottieAnimationView(getContext());
            animationView.setAnimation(R.raw.empty_list);
            frameLayout.addView(animationView);
            animationView.playAnimation();

            TextView tv= new TextView(getContext());
            tv.setText("No Favorite Movies");
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setTextSize(25);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(250,400,0,0);
            tv.setLayoutParams(params);

            frameLayout.addView(tv);
        }



        return view;


    }



}
