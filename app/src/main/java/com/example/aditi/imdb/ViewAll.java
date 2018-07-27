package com.example.aditi.imdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAll extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    MovieAdapter2 adapter;
    ArrayList<Movie> movies;
    Intent intent;
    String path,title;
    boolean isLoading=false;
    int currentItems,totalItems,scrolledItems;
    GridLayoutManager manager;
    int totalPages,currentPage=1;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        toolbar=findViewById(R.id.viewAlltoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     finish();
                                                 }
                                             }

        );
        progressBar=findViewById(R.id.progressBarForAll);
        recyclerView=findViewById(R.id.allMoviesView);
        movies=new ArrayList<>();
        adapter=new MovieAdapter2(this,movies);
        recyclerView.setAdapter(adapter);
        manager = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        intent = getIntent();
        path=intent.getStringExtra("path");
        title=intent.getStringExtra("title");
        toolbar.setTitle(title);

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
                    fetchMovies();
                }
                }
                });

                fetchMovies();
    }

    public void fetchMovies(){
        isLoading=true;
        progressBar.setVisibility(View.VISIBLE);
        if(currentPage==1) {

            recyclerView.setVisibility(View.GONE);
        }

        Call<FetchedMovie> call = ApiClient.getMoviesService().getMovies(path,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedMovie>() {
            @Override
            public void onResponse(Call<FetchedMovie> call, Response<FetchedMovie> response) {
                FetchedMovie fetchedMovie = response.body();
                ArrayList<Movie> movie = fetchedMovie.results; //get the arraylist of movies
                totalPages=fetchedMovie.total_pages;


                for(int i = 0;i<movie.size();i++){
                    movies.add(movie.get(i));

                }

                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                isLoading=false;

                if(currentPage==1) {

                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<FetchedMovie> call, Throwable t) {

            }
        });

    }
}
