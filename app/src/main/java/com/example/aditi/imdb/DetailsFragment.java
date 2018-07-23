package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

        String date=bundle.getString(Constants.DATE);
        int time=bundle.getInt(Constants.RUNTIME);
        String poster=bundle.getString(Constants.POSTER);
        String description = bundle.getString(Constants.DESCRIPTION);
        String tagline = bundle.getString(Constants.TAGLINE);
        Float rating =bundle.getFloat(Constants.RATING);

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


        return view;
    }

}
