package com.example.aditi.imdb;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentManager;
import com.airbnb.lottie.LottieAnimationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetail extends AppCompatActivity {
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    AppBarLayout appBarLayout;

    Intent intent;
    int showId;
    ImageView backdrop;
    TextView title,genres;
    TabLayout tabLayout;
    ViewPager viewPager;
    TV tvShow;
    FrameLayout frameLayout;
    Boolean isActivityLoaded=false,isBroadcastReceiverRegistered=false;
    private Snackbar mConnectivitySnackbar;
    private ConnectivityBroadcastReceiver mConnectivityBroadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
        toolbar=findViewById(R.id.activity_tvShow_toolbar);
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

        intent =getIntent();
        showId=intent.getIntExtra(Constants.ID,0);

        collapsingToolbarLayout=findViewById(R.id.activity_tvshow_detail_collapsing);
        appBarLayout=findViewById(R.id.activity_tvshow_detail_app_bar_layout);
        backdrop=findViewById(R.id.activity_tvshow_backdrop_image);
        viewPager=findViewById(R.id.activity_tvshow_viewPager);
        tabLayout=findViewById(R.id.activity_tvshow_tabLayout);
        title=findViewById(R.id.activity_tvshow_Title);
        genres=findViewById(R.id.activity_tvshow_Genres);
        frameLayout=findViewById(R.id.activity_tvshow_frameLayout);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));



        if (NetworkConnection.isConnected(TVShowDetail.this)) {
            isActivityLoaded = true;
            fetchDetails();

        }




    }

    @Override
    protected void onResume() {
        super.onResume();
        mConnectivitySnackbar= Snackbar.make(appBarLayout,R.string.no_connection, Snackbar.LENGTH_INDEFINITE);

        if (!isActivityLoaded && !NetworkConnection.isConnected(TVShowDetail.this)) {
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

        } else if (!isActivityLoaded && NetworkConnection.isConnected(TVShowDetail.this)) {
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
            if(tvShow!=null){
                Intent tvShareIntent = new Intent(Intent.ACTION_SEND);
                tvShareIntent.setType("text/plain");
                String extraText = "";
                if (tvShow.getName() != null) extraText += tvShow.getName() + "\n";
                if (tvShow.getHomepage() != null) extraText += tvShow.getHomepage();
                tvShareIntent.putExtra(Intent.EXTRA_TEXT, extraText);
                startActivity(tvShareIntent);
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

        Call<TV> call = ApiClient.getMoviesService().getTVShowDetails(showId,Constants.apiKey);
        call.enqueue(new Callback<TV>() {
            @Override
            public void onResponse(Call<TV> call, Response<TV> response) {
                tvShow = response.body();
                TVShowPageAdapter adapter = new TVShowPageAdapter(getSupportFragmentManager(),tvShow);
                viewPager.setAdapter(adapter);

                appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        if (appBarLayout.getTotalScrollRange() + verticalOffset == 0) {
                            if (tvShow.getName() != null)
                                collapsingToolbarLayout.setTitle(tvShow.getName());
                            else
                                collapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.VISIBLE);
                        } else {
                            collapsingToolbarLayout.setTitle("");
                            toolbar.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                title.setText(tvShow.name);
                String genre="";
                if (tvShow.genres != null) {
                    for (int i = 0; i < tvShow.genres.size(); i++) {
                        if (tvShow.genres.get(i) == null) continue;
                        if (i == tvShow.genres.size() - 1) {
                            genre = genre.concat(tvShow.genres.get(i).getName());
                        } else {
                            genre = genre.concat(tvShow.genres.get(i).getName() + ", ");
                        }
                    }
                }
                genres.setText(genre);
                animationView.setVisibility(View.GONE);
                backdrop.setVisibility(View.VISIBLE);
                Picasso.get().load(Constants.imageURL+tvShow.backdrop_path).into(backdrop);

            }
            @Override
            public void onFailure(Call<TV> call, Throwable t) {

            }
        });

    }

}
