package com.example.aditi.imdb;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TVShowsFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    ProgressBar progressBar;
    NestedScrollView scrollView;
    RecyclerView nowShowingView,topRatedView,popularView,onTheAirView;
    TVShowsAdapter1 adapter1,adapter2;
    TVShowsAdapter2 adapter3,adapter4;
    TextView tv1,tv2,tv3,tv4;
    ArrayList<TV> airingTodayShows,popularShows,onTheAirShows,topRatedShows;


    public TVShowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchShows("airing_today",airingTodayShows,adapter1);
        fetchShows("on_the_air",onTheAirShows,adapter2);
        fetchShows2("top_rated",topRatedShows,adapter4);
        fetchShows2("popular",popularShows,adapter3);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output= inflater.inflate(R.layout.fragment_tvshows, container, false);


        progressBar=output.findViewById(R.id.fragment_tvshows_progressBar);
        scrollView=output.findViewById(R.id.fragment_tvshows_scrollView);
        nowShowingView=output.findViewById(R.id.fragment_tvshows_nowShowingView);
        topRatedView=output.findViewById(R.id.fragment_tvshows_topRatedView);
        popularView=output.findViewById(R.id.fragment_tvshows_popularView);
        onTheAirView=output.findViewById(R.id.fragment_tvshows_onTheAirView);

        tv1=output.findViewById(R.id.fragment_tvshows_viewNowShowing);
        tv2=output.findViewById(R.id.fragment_tvshows_viewPopular);
        tv3=output.findViewById(R.id.fragment_tvshows_viewOnTheAir);
        tv4=output.findViewById(R.id.fragment_tvshows_viewTopRated);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

        airingTodayShows=new ArrayList<>();
        onTheAirShows=new ArrayList<>();
        popularShows=new ArrayList<>();
        topRatedShows=new ArrayList<>();


        adapter1=new TVShowsAdapter1(getContext(),airingTodayShows);
        adapter2=new TVShowsAdapter1(getContext(),onTheAirShows);
        adapter3=new TVShowsAdapter2(getContext(),popularShows);
        adapter4=new TVShowsAdapter2(getContext(),topRatedShows);


        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        nowShowingView.setLayoutManager(layoutManager1);
        nowShowingView.addItemDecoration( new LayoutMarginDecoration(1,20));
        nowShowingView.setAdapter(adapter1);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        onTheAirView.setLayoutManager(layoutManager4);
        onTheAirView.addItemDecoration( new LayoutMarginDecoration(1,20));
        onTheAirView.setAdapter(adapter2);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        popularView.setLayoutManager(layoutManager3);
        popularView.addItemDecoration( new LayoutMarginDecoration(1,20));
        popularView.setAdapter(adapter3);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        topRatedView.setLayoutManager(layoutManager2);
        topRatedView.addItemDecoration( new LayoutMarginDecoration(1,20));
        topRatedView.setAdapter(adapter4);




        return output;
    }

    public void fetchShows(String path,final ArrayList<TV> shows,final TVShowsAdapter1 adapter){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        Call<FetchedTVshows> call = ApiClient.getMoviesService().getShows(path,Constants.apiKey,1);
        call.enqueue(new Callback<FetchedTVshows>() {
            @Override
            public void onResponse(Call<FetchedTVshows> call, Response<FetchedTVshows> response) {
                FetchedTVshows fetchedshow = response.body();
                ArrayList<TV> show = fetchedshow.results; //get the arraylist of movies
                shows.clear();

                for(int i = 0;i<show.size();i++){
                    shows.add(show.get(i));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<FetchedTVshows> call, Throwable t) {

            }
        });


    }

    public void fetchShows2(String path,final ArrayList<TV> shows,final TVShowsAdapter2 adapter){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        Call<FetchedTVshows> call = ApiClient.getMoviesService().getShows(path,Constants.apiKey,1);
        call.enqueue(new Callback<FetchedTVshows>() {
            @Override
            public void onResponse(Call<FetchedTVshows> call, Response<FetchedTVshows> response) {
                FetchedTVshows fetchedshow = response.body();
                ArrayList<TV> show = fetchedshow.results; //get the arraylist of movies
                shows.clear();

                for(int i = 0;i<show.size();i++){
                    shows.add(show.get(i));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<FetchedTVshows> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View v) {

        int id = v.getId();
        Intent intent=new Intent(getContext(),ViewAllShows.class);
        if(id==R.id.fragment_tvshows_viewNowShowing){

            intent.putExtra("path","airing_today");
            intent.putExtra("title","Airing Today Shows");
        }
        else if(id==R.id.fragment_tvshows_viewPopular){
            intent.putExtra("path","popular");
            intent.putExtra("title","Popular Shows");

        }
        else if(id==R.id.fragment_tvshows_viewOnTheAir){
            intent.putExtra("path","on_the_air");
            intent.putExtra("title","On The Air Shows");

        }
        else if(id==R.id.fragment_tvshows_viewTopRated){

            intent.putExtra("path","top_rated");
            intent.putExtra("title","Top Rated Shows");

        }

        startActivity(intent);
    }



}
