package com.example.aditi.imdb;


import android.content.Intent;
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

public class CastFragment extends android.support.v4.app.Fragment {

    int id;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    View view;
    ArrayList<Cast> casts;
    CastAdapter adapter;
    GridLayoutManager manager;
    public CastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle bundle=getArguments();
        id= bundle.getInt(Constants.ID);
        view= inflater.inflate(R.layout.fragment_cast, container, false);
        progressBar=view.findViewById(R.id.castProgressBar);
        recyclerView=view.findViewById(R.id.castView);
        casts=new ArrayList<>();

        adapter=new CastAdapter(getContext(),casts);
        manager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        fetchCast();


        return view;
    }


    public void fetchCast(){
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        Call<FetchedCast> call = ApiClient.getMoviesService().getCast(id,Constants.apiKey);
        call.enqueue(new Callback<FetchedCast>() {
            @Override
            public void onResponse(Call<FetchedCast> call, Response<FetchedCast> response) {
                FetchedCast fetchedCast = response.body();
                List<Cast> cast = fetchedCast.getCast(); //get the arraylist of movies


                for(int i = 0;i<cast.size();i++){
                    casts.add(cast.get(i));
                }

                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onFailure(Call<FetchedCast> call, Throwable t) {

            }
        });

    }

}
