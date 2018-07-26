package com.example.aditi.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends Activity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    TextView textView;
    SearchAdapter searchAdapter;
    ArrayList<SearchResult> results;
    String query;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        toolbar= (Toolbar)findViewById(R.id.search_activity_toolbar);
       // setSupportActionBar(toolbar);
      //  getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     finish();
                                                 }
                                             }

        );


        recyclerView=findViewById(R.id.search_item_recycler_view);
        textView=findViewById(R.id.search_item_not_found_view);
        frameLayout=findViewById(R.id.activity_search_framelayout);

        results=new ArrayList<>();
        searchAdapter =new SearchAdapter(this,results);
        recyclerView.setAdapter(searchAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager1);

        Intent intent=getIntent();
        query=intent.getStringExtra(Constants.QUERY);
        search();

        toolbar.setTitle(query);//set query as title on toolbar

    }


    public void search(){
        Call<SearchResponse> call=ApiClient.getMoviesService().search(Constants.apiKey,query);
        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse searchResponse=response.body();
                    List<SearchResult> result= searchResponse.results;
                    if(!result.isEmpty()){
                    for(int i=0;i<result.size();i++){
                        results.add(result.get(i));
                    }
                    searchAdapter.notifyDataSetChanged();
                    recyclerView.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.GONE);
                }
                else {
                    //no result found
                        recyclerView.setVisibility(View.GONE);

                        LottieAnimationView animationView=new LottieAnimationView(SearchActivity.this);
                        animationView.setAnimation(R.raw.empty_list);
                        frameLayout.addView(animationView);
                        animationView.playAnimation();
                        textView.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {

            }
        });
    }


}
