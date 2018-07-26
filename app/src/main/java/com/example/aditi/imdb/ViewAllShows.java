package com.example.aditi.imdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewAllShows extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    TVShowsAdapter2 adapter;
    ArrayList<TV> shows;
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
        setContentView(R.layout.activity_view_all_shows);

        toolbar=findViewById(R.id.viewShowsAlltoolbar);
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

        progressBar=findViewById(R.id.progressBarForAllShows);
        recyclerView=findViewById(R.id.allShowsView);
        shows=new ArrayList<>();
        adapter=new TVShowsAdapter2(this,shows);
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
                    fetchShows();
                }
            }
        });

        fetchShows();
    }

    public void fetchShows(){
        isLoading=true;
        if(currentPage==1) {
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        Call<FetchedTVshows> call = ApiClient.getMoviesService().getShows(path,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedTVshows>() {
            @Override
            public void onResponse(Call<FetchedTVshows> call, Response<FetchedTVshows> response) {
                FetchedTVshows fetchedshow = response.body();
                ArrayList<TV> show = fetchedshow.results; //get the arraylist of movies
                totalPages=fetchedshow.total_pages;


                for(int i = 0;i<show.size();i++){
                    shows.add(show.get(i));

                }

                adapter.notifyDataSetChanged();
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
