package com.example.aditi.imdb;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FavPageAdapter extends FragmentPagerAdapter {

    public FavPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        if(position==0){
            FavMoviesFragment fragment1=new FavMoviesFragment();
            return  fragment1;
        }
        else if(position==1){
            FavTvShowsFragment fragment2=new FavTvShowsFragment();
            return fragment2;
        }

        return null;
    }


    @Override
    public int getCount() {
        return 2;
    }
}
