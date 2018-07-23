package com.example.aditi.imdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

public class PageAdapter extends FragmentPagerAdapter {

    Movie movie;

    public PageAdapter(FragmentManager fm,Movie movie) {
        super(fm);
        this.movie=movie;

    }

    @Override
    public Fragment getItem(int i) {
        Bundle b=new Bundle();

        if(i == 0){
            DetailsFragment fragment=new DetailsFragment();
            b.putString(Constants.DESCRIPTION,movie.overview);
            b.putString(Constants.DATE,movie.releaseDate);
            b.putString(Constants.POSTER,movie.posterPath);
            b.putInt(Constants.RUNTIME,movie.runtime);
            b.putString(Constants.TAGLINE,movie.tagline);
            b.putFloat(Constants.RATING,movie.rating);
            b.putInt(Constants.ID,movie.id);
            b.putString(Constants.TITLE,movie.title);
            fragment.setArguments(b);
            return fragment;
        }
        else if(i == 1){
            CastFragment fragment= new CastFragment();
            b.putInt(Constants.ID,movie.id);
            fragment.setArguments(b);
            return fragment;
        }
        else if(i == 2){
            ReviewsFragment fragment= new ReviewsFragment();
            b.putInt(Constants.ID,movie.id);
            fragment.setArguments(b);
            return fragment;
        }
        else if(i==3){
            SimilarMoviesFragment fragment= new SimilarMoviesFragment();
            b.putInt(Constants.ID,movie.id);
            fragment.setArguments(b);
            return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}