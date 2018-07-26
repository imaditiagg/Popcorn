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

        TextView releaseDateView = view.findViewById(R.id.releaseDate);
        TextView runtimeView=view.findViewById(R.id.runtime);
        final ImageView posterView= view.findViewById(R.id.moviePoster);

        TextView taglineView = view.findViewById(R.id.tagline);
        TextView descView=view.findViewById(R.id.overView);
        TextView ratingView = view.findViewById(R.id.rating);
        final Button favButton=view.findViewById(R.id.favButton);
        RecyclerView trailerRecyclerView=view.findViewById(R.id.recycler_view_trailers_movie_detail);
        trailerView = view.findViewById(R.id.text_view_trailer_movie_detail);
        trailerRecyclerView.setAdapter(videoAdapter);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        trailerRecyclerView.setLayoutManager(layoutManager);

        //obtain details

        String date=bundle.getString(Constants.DATE);
        int time=bundle.getInt(Constants.RUNTIME);
        String poster=bundle.getString(Constants.POSTER);
        String description = bundle.getString(Constants.DESCRIPTION);
        String tagline = bundle.getString(Constants.TAGLINE);
        Float rating =bundle.getFloat(Constants.RATING);
        int id=bundle.getInt(Constants.ID);
        String title=bundle.getString(Constants.TITLE);

        //set details

        releaseDateView.setText(releaseDateView.getText()+ date);
        runtimeView.setText(runtimeView.getText()+String.valueOf(time)+" Minutes");
        taglineView.setText(tagline);
        descView.setText(description);
        ratingView.setText(String.valueOf(rating) +"/10" );

        Picasso.get().load(Constants.imageURL2+poster).into(posterView);


        fetchTrailer(id);

        //check if movie is added to fav or not
        if (Favorite.isMovieFav(getContext(), id)) {
            favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
            favButton.setEnabled(true);
        } else {
            favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
            favButton.setEnabled(true);
        }


        final Movie movie = new Movie(id,title,poster);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Favorite.isMovieFav(getContext(),movie.getId())) {
                    Favorite.addMovieToFav(getContext(), movie);
                    favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(getContext(), "Added to Favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    Favorite.removeMovieFromFav(getContext(),movie.id);
                    favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    Toast.makeText(getContext(), "Removed from Favorites", Toast.LENGTH_SHORT).show();

                }
            }
        });




        return view;
    }

    public void fetchTrailer(int id){
        Log.i("Fn","called");

        Call<FetchedVideo> call = ApiClient.getMoviesService().getVideos(id,Constants.apiKey);
        call.enqueue(new retrofit2.Callback<FetchedVideo>() {
            @Override
            public void onResponse(Call<FetchedVideo> call, Response<FetchedVideo> response) {
                FetchedVideo fetchedVideo = response.body();
                List<Video> video = fetchedVideo.getVideos(); //get the arraylist of movies


                for(int i = 0;i<video.size();i++){
                    Video v= video.get(i);
                    if(v!=null && v.getSite()!=null && v.getSite().equals("YouTube") && v.getType()!=null && v.getType().equals("Trailer"))
                    videos.add(v);
                }

                if (!videos.isEmpty()) {
                    trailerView.setVisibility(View.VISIBLE);
                    videoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<FetchedVideo> call, Throwable t) {

            }
        });




    }


}
