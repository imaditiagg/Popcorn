package com.example.aditi.imdb;

import android.content.Intent;
import android.provider.ContactsContract;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
    android.support.v7.widget.SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mainContainer=findViewById(R.id.main_container);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.moviesItem);
        toolbar.setTitle(R.string.movies);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_container,new MoviesFragment());
        fragmentTransaction.commit();

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu,menu);
        final MenuItem menuItem = menu.findItem(R.id.searchItem);
        searchView = (android.support.v7.widget.SearchView) menuItem.getActionView();

        searchView.setQueryHint(getResources().getString(R.string.queryhint));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra(Constants.QUERY, query);
                    startActivity(intent);
                    menuItem.collapseActionView();
                    return true;
                }
                return false;

            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
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
        drawer.closeDrawer(GravityCompat.START);

        if (id == R.id.moviesItem) {

            toolbar.setTitle(R.string.movies);
            MoviesFragment moviesFragment=new MoviesFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            transaction.replace(R.id.main_container,moviesFragment);
            transaction.commit();
            return true;

        }
        else if (id == R.id.tvShowsItem) {
            toolbar.setTitle(R.string.tvShows);

            TVShowsFragment tvShowsFragment=new TVShowsFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();

            FragmentTransaction transaction= fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container,tvShowsFragment);
            transaction.commit();
            return true;

        }
        else if (id == R.id.favItem){
            toolbar.setTitle(R.string.fav);

            FavoritesFragment favoritesFragment=new FavoritesFragment();
            FragmentManager fragmentManager=getSupportFragmentManager();

            FragmentTransaction transaction= fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container,favoritesFragment);
            transaction.commit();
            return true;


        }
        else if(id==R.id.popularPeopleItem){
            toolbar.setTitle(R.string.popular);

            PopularPeopleFragment fragment=new PopularPeopleFragment();

            FragmentManager fragmentManager =getSupportFragmentManager();
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.replace(R.id.main_container,fragment);
            transaction.commit();
            return true;
        }
        else if(id==R.id.about){
            Intent intent=new Intent(this,AboutActivity.class);
            startActivity(intent);


        }



        return false;
    }
}

