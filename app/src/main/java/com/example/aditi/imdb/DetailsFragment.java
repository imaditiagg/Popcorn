package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import jp.wasabeef.blurry.Blurry;


public class DetailsFragment extends android.support.v4.app.Fragment {


    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_details, container, false);
        Bundle bundle = getArguments();

        TextView releaseDateView = view.findViewById(R.id.releaseDate);
        TextView runtimeView=view.findViewById(R.id.runtime);
        final ImageView posterView= view.findViewById(R.id.moviePoster);
        final LinearLayout layout =view.findViewById(R.id.bglayout);
        TextView taglineView = view.findViewById(R.id.tagline);
        TextView descView=view.findViewById(R.id.overView);
        TextView ratingView = view.findViewById(R.id.rating);
        final Button favButton=view.findViewById(R.id.favButton);

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
        Picasso.get().load(Constants.imageURL+poster).into(posterView,new Callback() {
            @Override
            public void onSuccess() {

             //   layout.setBackground(posterView.getDrawable());

            }

            @Override
            public void onError(Exception e) {

            }


        });

        //check if movie is added to fav or not
        if (Favorite.isMovieFav(getContext(), id)) {
            favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
            favButton.setEnabled(true);
        } else {
            favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
            favButton.setEnabled(true);
        }

        //add fav movie to db
        final Movie movie = new Movie(id,title,poster);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Favorite.isMovieFav(getContext(),movie.getId())) {
                    Favorite.addMovieToFav(getContext(), movie);
                    favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(getContext(), "Movie added to Favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    Favorite.removeMovieFromFav(getContext(),movie.id);
                    favButton.setBackground(getContext().getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    Toast.makeText(getContext(), "Movie removed from Favorites", Toast.LENGTH_SHORT).show();

                }
            }
        });


        return view;
    }

}
