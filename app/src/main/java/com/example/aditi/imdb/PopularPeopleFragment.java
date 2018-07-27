package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PopularPeopleFragment extends android.support.v4.app.Fragment {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    View view;
    ArrayList<Cast> casts;
    CastAdapter adapter;
    GridLayoutManager manager;
    boolean isLoading=false;
    int currentItems,totalItems,scrolledItems;
    int totalPages,currentPage=1;

    public PopularPeopleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_popular_people, container, false);

        progressBar=view.findViewById(R.id.popularPeopleProgressBar);
        recyclerView=view.findViewById(R.id.popularPeopleView);
        casts=new ArrayList<>();

        adapter=new CastAdapter(getContext(),casts);
        manager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

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
                    fetchPeople();
                }
            }
        });

        fetchPeople();
        return view;
    }

    public void fetchPeople(){
        isLoading=true;
        progressBar.setVisibility(View.VISIBLE);
        if(currentPage==1) {

            recyclerView.setVisibility(View.GONE);
        }
        Call<FetchedPopularPeople> call = ApiClient.getMoviesService().getPopularCast(Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedPopularPeople>() {
            @Override
            public void onResponse(Call<FetchedPopularPeople> call, Response<FetchedPopularPeople> response) {
                FetchedPopularPeople fetchedCast = response.body();
                List<Cast> cast = fetchedCast.results; //get the arraylist of cast
                totalPages=fetchedCast.total_pages;

                for(int i = 0;i<cast.size();i++){
                    casts.add(cast.get(i));
                }

                progressBar.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();
                isLoading=false;
                if(currentPage==1) {

                    recyclerView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onFailure(Call<FetchedPopularPeople> call, Throwable t) {

            }
        });

    }

}
