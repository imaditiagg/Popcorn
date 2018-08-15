package com.example.aditi.imdb;


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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SimilarMoviesFragment extends android.support.v4.app.Fragment {

    ArrayList<Movie> similarMovies;
    ArrayList<TV> shows;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter2 adapter;
    TVShowsAdapter2 tvShowsAdapter;
    boolean isLoading=false;
    int currentItems,totalItems,scrolledItems;
    GridLayoutManager manager;
    int totalPages,currentPage;
    int movieId;
    String type;


    public SimilarMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentPage=1;
        View view=inflater.inflate(R.layout.fragment_similar_movies, container, false);
        Bundle bundle= getArguments();
        movieId= bundle.getInt(Constants.ID);
        type=bundle.getString(Constants.TYPE);
        similarMovies=new ArrayList<>();
        shows=new ArrayList<>();
        progressBar=view.findViewById(R.id.progressBarForSimilarMovies);
        recyclerView=view.findViewById(R.id.similarMoviesView);
        adapter=new MovieAdapter2(getContext(),similarMovies);
        tvShowsAdapter=new TVShowsAdapter2(getContext(),shows);
        if(type.equals(Constants.MOVIETYPE))
            recyclerView.setAdapter(adapter);
        else
            recyclerView.setAdapter(tvShowsAdapter);

        manager = new GridLayoutManager(getContext(),3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);


        //fetch similar movies

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currentItems=manager.getChildCount();
                totalItems=manager.getItemCount();
                scrolledItems=manager.findFirstVisibleItemPosition();
                if(!isLoading && currentItems+scrolledItems==totalItems && currentPage<totalPages){
                    currentPage+=1;
                    if(type.equals(Constants.MOVIETYPE))
                        fetchSimilarMovies();
                    else
                        fetchSimilarShows();
                }
            }
        });



        if(type.equals(Constants.MOVIETYPE))
            fetchSimilarMovies();
        else fetchSimilarShows();
        return view;

    }


    public void fetchSimilarMovies(){
        isLoading=true;

        if(currentPage==1) {

            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Call<FetchedMovie> call = ApiClient.getMoviesService().getSimilarMovies(movieId,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedMovie>() {
            @Override
            public void onResponse(Call<FetchedMovie> call, Response<FetchedMovie> response) {
                FetchedMovie fetchedMovie = response.body();
                ArrayList<Movie> movie = fetchedMovie.results; //get the arraylist of movies
                totalPages=fetchedMovie.total_pages;
                for(int i = 0;i<movie.size();i++){

                    similarMovies.add(movie.get(i));

                }


                adapter.notifyDataSetChanged();

                isLoading=false;
                if(currentPage==1) {

                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<FetchedMovie> call, Throwable t) {

            }
        });

    }

    public void fetchSimilarShows(){
        isLoading=true;

        if(currentPage==1) {

            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Call<FetchedTVshows> call = ApiClient.getMoviesService().getSimilarShows(movieId,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedTVshows>() {
            @Override
            public void onResponse(Call<FetchedTVshows> call, Response<FetchedTVshows> response) {
                FetchedTVshows fetchedShow = response.body();
                if(fetchedShow!=null) {
                    ArrayList<TV> tv = fetchedShow.results; //get the arraylist of movies
                    totalPages = fetchedShow.total_pages;
                    for (int i = 0; i < tv.size(); i++) {

                        shows.add(tv.get(i));

                    }

                    adapter.notifyDataSetChanged();
                }

                isLoading=false;
                if(currentPage==1) {
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                }
            }
            @Override
            public void onFailure(Call<FetchedTVshows> call, Throwable t) {

            }
        });

    }

}
