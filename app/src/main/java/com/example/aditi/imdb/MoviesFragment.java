package com.example.aditi.imdb;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviesFragment extends android.support.v4.app.Fragment implements  View.OnClickListener {

    ProgressBar progressBar;
    NestedScrollView scrollView;
    RecyclerView nowShowingView,topRatedView,popularView,upcomingView;
    MovieAdapter1 adapter1,adapter2;
    MovieAdapter2 adapter3,adapter4;
    TextView tv1,tv2,tv3,tv4;
    ArrayList<Movie> nowShowingMovies,topRatedMovies,popularMovies,upcomingMovies;
    MovieDao movieDao;


    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View output=inflater.inflate(R.layout.fragment_movies, container, false);

        progressBar=output.findViewById(R.id.progressBar);
        scrollView=output.findViewById(R.id.scrollView);
        nowShowingView=output.findViewById(R.id.nowShowingView);
        topRatedView=output.findViewById(R.id.topRatedView);
        popularView=output.findViewById(R.id.popularView);
        upcomingView=output.findViewById(R.id.upcomingView);

        tv1=output.findViewById(R.id.viewNowShowing);
        tv2=output.findViewById(R.id.viewPopular);
        tv3=output.findViewById(R.id.viewUpcoming);
        tv4=output.findViewById(R.id.viewTopRated);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);


        nowShowingMovies =new ArrayList<>();
        topRatedMovies=new ArrayList<>();
        popularMovies=new ArrayList<>();
        upcomingMovies=new ArrayList<>();

        adapter1=new MovieAdapter1(getContext(),nowShowingMovies);
        adapter4=new MovieAdapter2(getContext(),topRatedMovies);
        adapter3=new MovieAdapter2(getContext(),popularMovies);
        adapter2=new MovieAdapter1(getContext(),upcomingMovies);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        nowShowingView.setLayoutManager(layoutManager1);
        nowShowingView.addItemDecoration( new LayoutMarginDecoration(1,20));
        nowShowingView.setAdapter(adapter1);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        topRatedView.setLayoutManager(layoutManager2);
        topRatedView.addItemDecoration( new LayoutMarginDecoration(1,20));
        topRatedView.setAdapter(adapter4);

        LinearLayoutManager layoutManager3 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        popularView.setLayoutManager(layoutManager3);
        popularView.addItemDecoration( new LayoutMarginDecoration(1,20));
        popularView.setAdapter(adapter3);

        LinearLayoutManager layoutManager4 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        upcomingView.setLayoutManager(layoutManager4);
        upcomingView.addItemDecoration( new LayoutMarginDecoration(1,20));
        upcomingView.setAdapter(adapter2);




        fetchMovies("now_playing",nowShowingMovies,adapter1);
        fetchMovies("upcoming",upcomingMovies,adapter2);
        fetchMovies2("top_rated",topRatedMovies,adapter4);
        fetchMovies2("popular",popularMovies,adapter3);
        return output;
    }

    public void fetchMovies(String path,final ArrayList<Movie> movies,final MovieAdapter1 adapter){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        Call<FetchedMovie> call = ApiClient.getMoviesService().getMovies(path,Constants.apiKey,1);
        call.enqueue(new Callback<FetchedMovie>() {
            @Override
            public void onResponse(Call<FetchedMovie> call, Response<FetchedMovie> response) {
                FetchedMovie fetchedMovie = response.body();
                ArrayList<Movie> movie = fetchedMovie.results; //get the arraylist of movies
                movies.clear();

                for(int i = 0;i<movie.size();i++){
                    movies.add(movie.get(i));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<FetchedMovie> call, Throwable t) {

            }
        });

    }
    public void fetchMovies2(String path,final ArrayList<Movie> movies,final MovieAdapter2 adapter){
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        Call<FetchedMovie> call = ApiClient.getMoviesService().getMovies(path,Constants.apiKey,1);
        call.enqueue(new Callback<FetchedMovie>() {
            @Override
            public void onResponse(Call<FetchedMovie> call, Response<FetchedMovie> response) {
                FetchedMovie fetchedMovie = response.body();
                ArrayList<Movie> movie = fetchedMovie.results; //get the arraylist of movies
                movies.clear();

                for(int i = 0;i<movie.size();i++){
                    movies.add(movie.get(i));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<FetchedMovie> call, Throwable t) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Intent intent=new Intent(getContext(),ViewAll.class);
        if(id==R.id.viewNowShowing){

            intent.putExtra("path","now_playing");
            intent.putExtra("title","Now Showing Movies");
        }
        else if(id==R.id.viewPopular){
            intent.putExtra("path","popular");
            intent.putExtra("title","Popular Movies");

        }
        else if(id==R.id.viewUpcoming){
            intent.putExtra("path","upcoming");
            intent.putExtra("title","Upcoming Movies");

        }
        else if(id==R.id.viewTopRated){

            intent.putExtra("path","top_rated");
            intent.putExtra("title","Top Rated Movies");

        }

        startActivity(intent);
    }

}
