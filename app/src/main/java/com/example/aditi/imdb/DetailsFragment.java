package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import retrofit2.Call;
import retrofit2.Response;


public class DetailsFragment extends android.support.v4.app.Fragment {

    VideoAdapter videoAdapter;
    ArrayList<Video> videos;
    TextView trailerView;
    String date,description,poster,tagline,title;
    int id,time;
    float rating;
    Button favButton;
    TextView releaseDateView,runtimeView,taglineView,descView,ratingView;
    ImageView posterView;
    RecyclerView trailerRecyclerView;
    String type;


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

        releaseDateView = view.findViewById(R.id.releaseDate);
        runtimeView=view.findViewById(R.id.runtime);
        posterView= view.findViewById(R.id.moviePoster);

        taglineView = view.findViewById(R.id.tagline);
        descView=view.findViewById(R.id.overView);
        ratingView = view.findViewById(R.id.rating);
        favButton=view.findViewById(R.id.favButton);

        trailerRecyclerView=view.findViewById(R.id.recycler_view_trailers_movie_detail);
        trailerView = view.findViewById(R.id.text_view_trailer_movie_detail);
        trailerRecyclerView.setAdapter(videoAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        trailerRecyclerView.setLayoutManager(layoutManager);

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

        fetchTrailer(id);

        if(type.equals(Constants.MOVIETYPE)) {

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

    public void fetchTrailer(int id){

        Call<FetchedVideo> call = ApiClient.getMoviesService().getVideos(id,Constants.apiKey);
        call.enqueue(new retrofit2.Callback<FetchedVideo>() {
            @Override
            public void onResponse(Call<FetchedVideo> call, Response<FetchedVideo> response) {
                FetchedVideo fetchedVideo = response.body();
                if(fetchedVideo!=null) {
                    List<Video> video = fetchedVideo.getVideos(); //get the arraylist of movies


                    for (int i = 0; i < video.size(); i++) {
                        Video v = video.get(i);
                        if (v != null && v.getSite() != null && v.getSite().equals("YouTube") && v.getType() != null && v.getType().equals("Trailer"))
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


}
