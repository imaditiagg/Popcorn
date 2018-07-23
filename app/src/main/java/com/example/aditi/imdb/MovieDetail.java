package com.example.aditi.imdb;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {
    Intent intent;
    int movieId;
    ImageView backdrop,poster;
    TextView title,genres;
    Toolbar toolbar;
    MovieDao movieDao;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    Movie movie;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        collapsingToolbarLayout = findViewById(R.id.collapsing);
        appBarLayout=findViewById(R.id.activity_movie_detail_app_bar_layout);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        title=findViewById(R.id.movieTitle);
        backdrop=findViewById(R.id.backdrop_image);


        genres=findViewById(R.id.movieGenres);
        MovieDatabase database = Room.databaseBuilder(this.getApplicationContext(),MovieDatabase.class,"movies_db").allowMainThreadQueries().build();
        movieDao= database.getMovieDao();


        intent=getIntent();
        movieId=intent.getIntExtra(Constants.ID,0);
        fetchDetails();



    }

    public void fetchDetails(){

        Call<Movie> call = ApiClient.getMoviesService().getDetails(movieId,Constants.apiKey);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),movie);
                viewPager.setAdapter(adapter);

               appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                            if (movie.getTitle() != null)
                                collapsingToolbarLayout.setTitle(movie.getTitle());
                            else
                                collapsingToolbarLayout.setTitle("");
                                toolbar.setVisibility(View.VISIBLE);
                        } else {
                            collapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

               title.setText(movie.title);
               String genre="";
                if (movie.genres != null) {
                    for (int i = 0; i < movie.genres.size(); i++) {
                        if (movie.genres.get(i) == null) continue;
                        if (i == movie.genres.size() - 1) {
                            genre = genre.concat(movie.genres.get(i).getName());
                        } else {
                            genre = genre.concat(movie.genres.get(i).getName() + ", ");
                        }
                    }
                }
                genres.setText(genre);
                Picasso.get().load(Constants.imageURL+movie.backdropPath).into(backdrop);

            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }


}
