package com.example.aditi.imdb;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetail extends AppCompatActivity {
    Intent intent;
    int movieId;
    ImageView backdrop;
    TextView title,genres;
    Toolbar toolbar;

    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TabLayout tabLayout;
    ViewPager viewPager;
    Movie movie;
    FrameLayout frameLayout;
    Boolean isActivityLoaded=false,isBroadcastReceiverRegistered=false;
    private Snackbar mConnectivitySnackbar;
    private ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View view) {
                                                     finish();
                                                 }
                                             }

        );
        toolbar.setNavigationIcon(R.drawable.ic_chevron_left_white_24dp);

        collapsingToolbarLayout = findViewById(R.id.collapsing);
        appBarLayout=findViewById(R.id.activity_movie_detail_app_bar_layout);


        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        title=findViewById(R.id.movieTitle);
        backdrop=findViewById(R.id.backdrop_image);
        frameLayout=findViewById(R.id.activity_movie_detail_frameLayout);
        genres=findViewById(R.id.movieGenres);

        intent=getIntent();
        movieId=intent.getIntExtra(Constants.ID,0);

        if (NetworkConnection.isConnected(MovieDetail.this)) {
            isActivityLoaded = true;
            fetchDetails();

        }




    }


    @Override
    protected void onResume() {
        super.onResume();
        mConnectivitySnackbar= Snackbar.make(appBarLayout,R.string.no_connection, Snackbar.LENGTH_INDEFINITE);

        if (!isActivityLoaded && !NetworkConnection.isConnected(MovieDetail.this)) {
         mConnectivitySnackbar.show();

         mConnectivityBroadcastReceiver = new ConnectivityBroadcastReceiver(new ConnectivityBroadcastReceiver.ConnectivityReceiverListener() {
                @Override
                public void onNetworkConnectionConnected() {
                    mConnectivitySnackbar.dismiss();
                    isActivityLoaded = true;
                    fetchDetails();
                    isBroadcastReceiverRegistered = false;
                    unregisterReceiver(mConnectivityBroadcastReceiver);
                }
            });

            IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
            isBroadcastReceiverRegistered = true;
            registerReceiver(mConnectivityBroadcastReceiver, intentFilter);

        } else if (!isActivityLoaded && NetworkConnection.isConnected(MovieDetail.this)) {
            isActivityLoaded = true;
            fetchDetails();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (isBroadcastReceiverRegistered) {
            isBroadcastReceiverRegistered = false;
            unregisterReceiver(mConnectivityBroadcastReceiver);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.share){
            if(movie!=null){
                Intent movieShareIntent = new Intent(Intent.ACTION_SEND);
                movieShareIntent.setType("text/plain");
                String extraText = "";
                if (movie.getTitle() != null) extraText += movie.getTitle() + "\n";
                if (movie.getTagline() != null) extraText += movie.getTagline() + "\n";
                if (movie.getImdb_id() != null) extraText += Constants.IMDB_BASE_URL + movie.getImdb_id() + "\n";
                if (movie.getHomepage() != null) extraText += movie.getHomepage();
                movieShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(movieShareIntent);
            }
        }
        return true;
    }

    public void fetchDetails(){

        backdrop.setVisibility(View.GONE);
        final LottieAnimationView animationView=new LottieAnimationView(this);
        animationView.setAnimation(R.raw.loading_animation);
        animationView.playAnimation();
        animationView.setVisibility(View.VISIBLE);
        frameLayout.addView(animationView);


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
                animationView.setVisibility(View.GONE);
                backdrop.setVisibility(View.VISIBLE);

            }
            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        });

    }


}
