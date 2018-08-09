package com.example.aditi.imdb;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TVShowPageAdapter extends FragmentPagerAdapter {
    TV show;


    public TVShowPageAdapter(FragmentManager fm,TV show) {
        super(fm);
        this.show=show;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle b=new Bundle();

        if(i==0){
            DetailsFragment fragment=new DetailsFragment();
            b.putString(Constants.DESCRIPTION,show.overview);
            b.putString(Constants.DATE,show.first_air_date);
            b.putString(Constants.POSTER,show.poster_path);
            if(!show.episode_run_time.isEmpty())
            b.putInt(Constants.RUNTIME,show.episode_run_time.get(0));

            b.putFloat(Constants.RATING,show.vote_average);
            b.putInt(Constants.ID,show.id);
            b.putString(Constants.TITLE,show.name);
            b.putString(Constants.TYPE,Constants.TVTYPE);
            b.putInt(Constants.SEASONCOUNT,show.number_of_seasons);
            fragment.setArguments(b);
            return fragment;
        }

        else if(i == 1){
            CastFragment fragment= new CastFragment();
            b.putInt(Constants.ID,show.id);
            b.putString(Constants.TYPE,Constants.TVTYPE);
            fragment.setArguments(b);
            return fragment;
        }
        else if(i == 2){
            ReviewsFragment fragment= new ReviewsFragment();
            b.putInt(Constants.ID,show.id);
            b.putString(Constants.TYPE,Constants.TVTYPE);
            fragment.setArguments(b);
            return fragment;
        }
        else if(i==3){
            SimilarMoviesFragment fragment= new SimilarMoviesFragment();
            b.putInt(Constants.ID,show.id);
            b.putString(Constants.TYPE,Constants.TVTYPE);
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
