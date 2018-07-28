package com.example.aditi.imdb;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
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

    TextView tv;

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
        tv=view.findViewById(R.id.no_fav_movies);

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
            tv.setVisibility(View.GONE);

        }
        else{
            animationView=new LottieAnimationView(getContext());
            animationView.setAnimation(R.raw.empty_list);
            frameLayout.addView(animationView);
            animationView.playAnimation();
            tv.setVisibility(View.VISIBLE);

        }



        return view;


    }



}
