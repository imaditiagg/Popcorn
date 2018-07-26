package com.example.aditi.imdb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDetail extends Activity {

    Intent intent;
    android.support.v7.widget.Toolbar toolbar;
    long castId;
    TextView nameView,ageView,placeView,readMoreView,bioView,header,ageHeader,birthPlaceHeader,movieCastHeader,tvCastHeader;
    ImageView imageView;
    Person person;
    AppBarLayout appBarLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView movieCastRecyclerView,tvCastRecyclerView;
    MovieCastOfPersonAdapter adapter;
    ArrayList<MovieCast> movies;
    ArrayList<TVCast> tvShows;
    TVCastOfPersonAdapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cast_detail);
        toolbar=findViewById(R.id.person_details_toolbar);
       // setSupportActionBar(toolbar2);
       // getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     finish();
                                                 }
                                             }

        );
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);


        movies=new ArrayList<>();
        tvShows=new ArrayList<>();
        intent=getIntent();
        castId=intent.getLongExtra(Constants.CAST_ID,0);

        nameView=findViewById(R.id.personName);
        ageView=findViewById(R.id.personAge);
        placeView=findViewById(R.id.personBirthPlace);
        imageView=findViewById(R.id.castImage);
        readMoreView=findViewById(R.id.readMorePersonBio);
        bioView=findViewById(R.id.personBiography);
        header=findViewById(R.id.cast_bio_header);
        appBarLayout=findViewById(R.id.activity_cast_detail_app_bar_layout);
        collapsingToolbarLayout=findViewById(R.id.cast_collapsing_toolbar);
        movieCastRecyclerView=findViewById(R.id.movieCastView);
        tvCastRecyclerView=findViewById(R.id.tvCastView);
        ageHeader=findViewById(R.id.ageHeader);
        birthPlaceHeader=findViewById(R.id.birthplaceHeader);
        tvCastHeader=findViewById(R.id.tvCastHeader);
        movieCastHeader=findViewById(R.id.movieCastHeader);

        adapter=new MovieCastOfPersonAdapter(this,movies);
        adapter2=new TVCastOfPersonAdapter(this,tvShows);
        movieCastRecyclerView.setAdapter(adapter);
        tvCastRecyclerView.setAdapter(adapter2);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        movieCastRecyclerView.addItemDecoration( new LayoutMarginDecoration(1,20));
        movieCastRecyclerView.setLayoutManager(layoutManager1);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        tvCastRecyclerView.setLayoutManager(layoutManager2);
        tvCastRecyclerView.addItemDecoration( new LayoutMarginDecoration(1,20));

        fetchPersonDetails();
        fetchMovieCastOfPerson();
        fetchTVCastOfPerson();


    }

    public void fetchPersonDetails(){

        Call<Person> call = ApiClient.getMoviesService().getPersonDetails(castId,Constants.apiKey);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                person = response.body();

                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                            if (person.getName() != null){
                                collapsingToolbarLayout.setTitle(person.getName());
                        }
                            else
                                collapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.VISIBLE);
                        } else {
                            collapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
                if(person.getName()!=null){
                    nameView.setText(person.getName());
                }
                else{
                    nameView.setText("");
                }
                if(person.getPlaceOfBirth()!=null) {
                    placeView.setText(person.getPlaceOfBirth());
                }
                else {
                    birthPlaceHeader.setVisibility(View.GONE);
                    placeView.setText("");
                }
                if (person.getBirthday() != null && !person.getBirthday().trim().isEmpty()) {
                    SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat f2 = new SimpleDateFormat("yyyy");
                    try {
                        Date date = f1.parse(person.getBirthday());
                        ageView.setText((Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(f2.format(date))) + "");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    ageView.setText("");
                    ageHeader.setVisibility(View.GONE);
                }
                if (person.getBiography() != null && !person.getBiography().trim().isEmpty()) {
                    header.setVisibility(View.VISIBLE);
                    readMoreView.setVisibility(View.VISIBLE);
                    bioView.setText(person.getBiography());
                    readMoreView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           bioView.setMaxLines(Integer.MAX_VALUE);
                           readMoreView.setVisibility(View.GONE);
                        }
                    });
                }
                Picasso.get().load(Constants.imageURL2+person.getProfilePath()).into(imageView);

            }
            @Override
            public void onFailure(Call<Person> call, Throwable t) {

            }
        });



    }

    public void fetchMovieCastOfPerson(){

        movieCastRecyclerView.setVisibility(View.GONE);

        Call<MovieCastOfPerson> call = ApiClient.getMoviesService().getMovieCastOfPerson(castId,Constants.apiKey);
        call.enqueue(new Callback<MovieCastOfPerson>() {
            @Override
            public void onResponse(Call<MovieCastOfPerson> call, Response<MovieCastOfPerson> response) {
                MovieCastOfPerson fetchedResponse = response.body();
                ArrayList<MovieCast> movie = fetchedResponse.movieCasts; //get the arraylist of movies
                movies.clear();

                for(int i = 0;i<movie.size();i++){
                    movies.add(movie.get(i));

                }
                if(!movies.isEmpty()) {
                    adapter.notifyDataSetChanged();
                    movieCastRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    movieCastHeader.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<MovieCastOfPerson> call, Throwable t) {

            }
        });
    }

    public void fetchTVCastOfPerson(){

        tvCastRecyclerView.setVisibility(View.GONE);

        Call<TVCastOfPerson> call = ApiClient.getMoviesService().getTVCastOfPerson(castId,Constants.apiKey);
        call.enqueue(new Callback<TVCastOfPerson>() {
            @Override
            public void onResponse(Call<TVCastOfPerson> call, Response<TVCastOfPerson> response) {
                TVCastOfPerson fetchedResponse = response.body();
                ArrayList<TVCast> tv = fetchedResponse.tvCasts; //get the arraylist of movies
                tvShows.clear();

                for(int i = 0;i<tv.size();i++){
                    tvShows.add(tv.get(i));

                }
                if(!tvShows.isEmpty()) {
                    adapter2.notifyDataSetChanged();
                    tvCastRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    tvCastHeader.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<TVCastOfPerson> call, Throwable t) {

            }
        });
    }
}
