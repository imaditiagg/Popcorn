package com.example.aditi.imdb;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import jp.wasabeef.glide.transformations.BlurTransformation;
import retrofit2.Call;
import retrofit2.Response;


public class DetailsFragment extends android.support.v4.app.Fragment {

    VideoAdapter videoAdapter;
    SeasonsAdapter seasonsAdapter;
    ArrayList<Video> videos;
    ArrayList<Season> seasons;
    TextView trailerView,seasonView;
    String date,description,poster,tagline,title,imdbID;
    int id,time,seasonCount;
    float rating;
    Button favButton;
    TextView releaseDateView,runtimeView,taglineView,descView,ratingView;
    ImageView posterView;
    RecyclerView trailerRecyclerView,seasonsRecyclerView;
    String type;
    LinearLayout layout;


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();

        videos=new ArrayList<>();
        videoAdapter =new VideoAdapter(getContext(),videos);

        seasons=new ArrayList<>();
        seasonsAdapter=new SeasonsAdapter(getContext(),seasons);

        releaseDateView = view.findViewById(R.id.releaseDate);
        runtimeView=view.findViewById(R.id.runtime);
        posterView= view.findViewById(R.id.moviePoster);

        taglineView = view.findViewById(R.id.tagline);
        descView=view.findViewById(R.id.overView);
        ratingView = view.findViewById(R.id.rating);
        favButton=view.findViewById(R.id.favButton);
        layout=view.findViewById(R.id.bglayout);



        trailerRecyclerView=view.findViewById(R.id.recycler_view_trailers_movie_detail);
        trailerView = view.findViewById(R.id.text_view_trailer_movie_detail);
        trailerRecyclerView.setAdapter(videoAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        trailerRecyclerView.setLayoutManager(layoutManager);

        seasonsRecyclerView=view.findViewById(R.id.recycler_view_seasons);
        seasonView=view.findViewById(R.id.text_view_seasons);
        seasonsRecyclerView.setAdapter(seasonsAdapter);
        LinearLayoutManager layoutManager2= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        seasonsRecyclerView.addItemDecoration( new LayoutMarginDecoration(1,20));
        seasonsRecyclerView.setLayoutManager(layoutManager2);


        //obtain details

        date=bundle.getString(Constants.DATE);
        time=bundle.getInt(Constants.RUNTIME);
        poster=bundle.getString(Constants.POSTER);
        description = bundle.getString(Constants.DESCRIPTION);
        tagline = bundle.getString(Constants.TAGLINE);
        rating =bundle.getFloat(Constants.RATING);
        id=bundle.getInt(Constants.ID);
        title=bundle.getString(Constants.TITLE);
        type=bundle.getString(Constants.TYPE);
        seasonCount=bundle.getInt(Constants.SEASONCOUNT);

        //set details

        setDetails();
        return view;
    }


    public void setDetails(){

        if(!description.isEmpty()) {
            descView.setText(description);
            descView.setVisibility(View.VISIBLE);
        }
        else{
            descView.setText("");
            descView.setVisibility(View.GONE);
        }
        ratingView.setText(String.valueOf(rating) +"/10" );
        Picasso.get().load(Constants.imageURL2+poster).into(posterView);
        Glide.with(this).load(Constants.imageURL2+poster).apply(RequestOptions.bitmapTransform(new BlurTransformation(65)) ).into(new SimpleTarget<Drawable>() {
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    layout.setBackground(resource);
                }
                else{
                    layout.setBackgroundColor(getResources().getColor(R.color.darkGrey));
                }
            }
        });



        if(type.equals(Constants.MOVIETYPE)) {
            fetchMovieTrailer(id);

            taglineView.setText(tagline);
            releaseDateView.setText("Release Date : "+ date);
            runtimeView.setText("Duration : " +String.valueOf(time)+" Minutes");

            //check if movie is added to fav or not
            if (Favorite.isMovieFav(getContext(), id)) {
                favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                favButton.setEnabled(true);
            } else {
                favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                favButton.setEnabled(true);
            }


            final Movie movie = new Movie(id, title, poster);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Favorite.isMovieFav(getContext(), movie.getId())) {
                        Favorite.addMovieToFav(getContext(), movie);
                        favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                        Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Favorite.removeMovieFromFav(getContext(), movie.id);
                        favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                        Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else if(type.equals(Constants.TVTYPE)){
            fetchTVTrailer(id);

            if(!date.isEmpty())
                releaseDateView.setText("First Air Date : "+ date);
            else
                releaseDateView.setText("");

            runtimeView.setText(String.valueOf("Duration : " +String.valueOf(time)+" Minutes"));
            taglineView.setText("");

            if(Favorite.isShowFav(getContext(),id)){
                favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                favButton.setEnabled(true);
            }
            else {
                favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                favButton.setEnabled(true);
            }

            final TV tvShow = new TV(id, title, poster);
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!Favorite.isShowFav(getContext(), tvShow.getId())) {
                        Favorite.addShow(getContext(), tvShow);
                        favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                        Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                    } else {
                        Favorite.removeShowFromFav(getContext(), tvShow.getId());
                        favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                        Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }


    }

    public void fetchMovieTrailer(int id){

        Call<FetchedVideo> call = ApiClient.getMoviesService().getVideos(id,Constants.apiKey);
        call.enqueue(new retrofit2.Callback<FetchedVideo>() {
            @Override
            public void onResponse(Call<FetchedVideo> call, Response<FetchedVideo> response) {
                FetchedVideo fetchedVideo = response.body();
                if(fetchedVideo!=null) {
                    List<Video> video = fetchedVideo.getVideos(); //get the arraylist of videos


                    for (int i = 0; i < video.size(); i++) {
                        Video v = video.get(i);
                        if (v != null && v.getSite() != null && v.getSite().equals("YouTube") && v.getType() != null )
                            videos.add(v);
                    }

                    if (!videos.isEmpty()) {

                        trailerView.setVisibility(View.VISIBLE);
                        videoAdapter.notifyDataSetChanged();
                        if (type.equals(Constants.MOVIETYPE)) {
                            trailerView.setText(R.string.trailers);
                        } else {
                            trailerView.setText(R.string.videos);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FetchedVideo> call, Throwable t) {

            }
        });


    }

    public void fetchTVTrailer(int id){

        Call<FetchedVideo> call = ApiClient.getMoviesService().getTVvideos(id,Constants.apiKey);
        call.enqueue(new retrofit2.Callback<FetchedVideo>() {
            @Override
            public void onResponse(Call<FetchedVideo> call, Response<FetchedVideo> response) {
                FetchedVideo fetchedVideo = response.body();
                if(fetchedVideo!=null) {
                    List<Video> video = fetchedVideo.getVideos(); //get the arraylist of videos


                    for (int i = 0; i < video.size(); i++) {
                        Video v = video.get(i);
                        if (v != null && v.getSite() != null && v.getSite().equals("YouTube") && v.getType() != null )
                            videos.add(v);
                    }

                    if (!videos.isEmpty()) {

                        trailerView.setVisibility(View.VISIBLE);
                        videoAdapter.notifyDataSetChanged();
                        if (type.equals(Constants.MOVIETYPE)) {
                            trailerView.setText(R.string.trailers);
                        } else {
                            trailerView.setText(R.string.videos);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<FetchedVideo> call, Throwable t) {

            }
        });
        //fetch season details
        if(type.equals(Constants.TVTYPE)) {
            for(int i=1;i<seasonCount;i++) {
                fetchSeasons(i);
            }
        }


    }

    public void fetchSeasons(int seasonNo) {

        Call<Season> call = ApiClient.getMoviesService().getSeasons(id, seasonNo, Constants.apiKey);
        call.enqueue(new retrofit2.Callback<Season>() {

            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                Season season = response.body();
                if (season != null) {
                    seasonsRecyclerView.setVisibility(View.VISIBLE);
                    seasonView.setVisibility(View.VISIBLE);
                    seasons.add(season);
                    Collections.sort(seasons, new Comparator<Season>() {

                        public int compare(Season s1, Season s2) {
                            return s1.getAir_date().compareTo(s2.getAir_date());
                        }
                    });

                    seasonsAdapter.notifyDataSetChanged();
                    }


            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {

            }
        });


    }
}
