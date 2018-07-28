package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;


public class FavTvShowsFragment extends android.support.v4.app.Fragment {

    ArrayList<TV> shows;
    RecyclerView recyclerView;
    TVShowsAdapter2 adapter;
    FrameLayout frameLayout;
    LottieAnimationView animationView;
    TextView tv;

    public FavTvShowsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_fav_tv_shows, container, false);

        recyclerView=view.findViewById(R.id.fav_shows_recycler_view);
        frameLayout=view.findViewById(R.id.fragment_favShows_rootLayout);
        tv=view.findViewById(R.id.no_fav_shows);

        shows=new ArrayList<>();
        adapter = new TVShowsAdapter2(getContext(), shows);
        recyclerView.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);

        ArrayList<TV> shows1 = Favorite.loadFavShows(getContext());
        shows.addAll(shows1);

        if(!shows1.isEmpty()) {
            if(frameLayout.indexOfChild(animationView)>-1) {
                frameLayout.removeView(animationView);
            }
            adapter.notifyDataSetChanged();
            tv.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);


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

    @Override
    public void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();

    }

}
