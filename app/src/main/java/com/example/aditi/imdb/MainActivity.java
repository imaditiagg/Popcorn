package com.example.aditi.imdb;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    android.support.v7.widget.Toolbar toolbar;
    DrawerLayout drawer;
    FrameLayout mainContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mainContainer=findViewById(R.id.main_container);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.moviesItem);
        setTitle(R.string.movies);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new MoviesFragment());
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.moviesItem) {

            setTitle(R.string.movies);
            MoviesFragment moviesFragment=new MoviesFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.main_container,moviesFragment);
            transaction.commit();

        }
        else if (id == R.id.tvShowsItem) {
            setTitle(R.string.tvShows);

            TVShowsFragment tvShowsFragment=new TVShowsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();

            FragmentTransaction transaction= fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container,tvShowsFragment);
            transaction.commit();

        }
        else if (id == R.id.favItem){
            setTitle(R.string.fav);

            FavoritesFragment favoritesFragment=new FavoritesFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();

            FragmentTransaction transaction= fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container,favoritesFragment);
            transaction.commit();

        }


        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}

