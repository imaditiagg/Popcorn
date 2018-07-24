package com.example.aditi.imdb;


import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FavoritesFragment extends android.support.v4.app.Fragment {


    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_favorites, container, false);
        ViewPager viewPager=view.findViewById(R.id.favViewPager);
        TabLayout tabLayout=view.findViewById(R.id.favTabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        FavPageAdapter favPageAdapter=new FavPageAdapter(getChildFragmentManager());
        viewPager.setAdapter(favPageAdapter);


        return view;
    }

}
