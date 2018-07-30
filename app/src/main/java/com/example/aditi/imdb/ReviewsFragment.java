package com.example.aditi.imdb;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewsFragment extends android.support.v4.app.Fragment {


    int movieId;
    ArrayList<Review> reviews;
    ReviewsAdapter adapter;
    ListView reviewsView;
    ProgressBar progressBar;
    private static final int PAGE_START = 1;
    private int currentPage;
    TextView tv,noReviewsView;
    FrameLayout frameLayout;
    String type;
    LottieAnimationView lottieAnimationView;

    public ReviewsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentPage=PAGE_START;
        View view= inflater.inflate(R.layout.fragment_reviews, container, false);

        progressBar=view.findViewById(R.id.reviewsProgressBar);
        reviewsView=view.findViewById(R.id.reviewsList);
        frameLayout=view.findViewById(R.id.reviews_root_layout);
        ViewCompat.setNestedScrollingEnabled(reviewsView,true);
        noReviewsView=view.findViewById(R.id.no_reviews);
        lottieAnimationView=view.findViewById(R.id.empty_animation);



        Bundle bundle=getArguments();
        movieId= bundle.getInt(Constants.ID);
        type=bundle.getString(Constants.TYPE);

        reviews=new ArrayList<>();
        adapter=new ReviewsAdapter(getContext(),reviews);
        reviewsView.setAdapter(adapter);
        reviewsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Review review = reviews.get(position);
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
                View v = inflater.inflate(R.layout.review_dialog_view,null,false);
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setView(v);
                builder.setCancelable(true);
                TextView name = v.findViewById(R.id.userName);
                TextView content =v.findViewById(R.id.userReview);
                name.setText(review.getAuthor());
                content.setText(review.getContent());

                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
        if(type.equals(Constants.MOVIETYPE))
            fetchReviews();
        else
            fetchTVReviews();
        tv=new TextView(getContext());
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setPadding(5,5,5,5);
        tv.setText("Load More");
        tv.setTextSize(20);
        tv.setTextColor(getResources().getColor(R.color.yellow));
        reviewsView.addFooterView(tv,null,true);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    currentPage += 1;
                    if(type.equals(Constants.MOVIETYPE))
                        fetchReviews();
                    else
                        fetchTVReviews();

            }
        });

        return view;
    }


    public void fetchReviews(){
        if(currentPage==1) {
            progressBar.setVisibility(View.VISIBLE);
            reviewsView.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
            noReviewsView.setVisibility(View.GONE);

        }

        Call<FetchedReview> call = ApiClient.getMoviesService().getReviews(movieId,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedReview>() {
            @Override
            public void onResponse(Call<FetchedReview> call, Response<FetchedReview> response) {
                FetchedReview fetchedReview = response.body();
                List<Review> r= fetchedReview.getReviews(); //get the arraylist of movies

                if(!r.isEmpty()) {
                    for (int i = 0; i < r.size(); i++) {
                        reviews.add(r.get(i));

                    }
                    adapter.notifyDataSetChanged();
                    if(currentPage==1) {
                        noReviewsView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.GONE);
                    }

                }
                else{
                    if(currentPage==1){

                        progressBar.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        /*  LottieAnimationView animationView=new LottieAnimationView(getContext());
                        animationView.setAnimation(R.raw.empty_list);
                        frameLayout.addView(animationView);
                        animationView.playAnimation();*/
                        lottieAnimationView.setVisibility(View.VISIBLE);
                        noReviewsView.setVisibility(View.VISIBLE);

                    }
                    else {
                        Toast.makeText(getContext(), "No more reviews", Toast.LENGTH_SHORT).show();
                        tv.setVisibility(View.GONE);
                    }
                }

            }
            @Override
            public void onFailure(Call<FetchedReview> call, Throwable t) {

            }
        });

    }

    public void fetchTVReviews(){
        if(currentPage==1) {
            progressBar.setVisibility(View.VISIBLE);
            reviewsView.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.GONE);
            noReviewsView.setVisibility(View.GONE);
        }

        Call<FetchedReview> call = ApiClient.getMoviesService().getTVReviews(movieId,Constants.apiKey,currentPage);
        call.enqueue(new Callback<FetchedReview>() {
            @Override
            public void onResponse(Call<FetchedReview> call, Response<FetchedReview> response) {
                FetchedReview fetchedReview = response.body();
                List<Review> r= fetchedReview.getReviews(); //get the arraylist of movies

                if(!r.isEmpty()) {
                    for (int i = 0; i < r.size(); i++) {
                        reviews.add(r.get(i));

                    }
                    adapter.notifyDataSetChanged();
                    if(currentPage==1) {
                        progressBar.setVisibility(View.GONE);
                        noReviewsView.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.GONE);
                    }

                }
                else{
                    if(currentPage==1){

                        progressBar.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.GONE);
                        tv.setVisibility(View.GONE);
                        /* LottieAnimationView animationView=new LottieAnimationView(getContext());
                        animationView.setAnimation(R.raw.empty_list);
                        frameLayout.addView(animationView);
                        animationView.playAnimation();*/
                        noReviewsView.setVisibility(View.VISIBLE);
                        lottieAnimationView.setVisibility(View.VISIBLE);


                    }
                    else {
                        Toast.makeText(getContext(), "No more reviews", Toast.LENGTH_SHORT).show();
                        tv.setVisibility(View.GONE);
                    }
                }

            }
            @Override
            public void onFailure(Call<FetchedReview> call, Throwable t) {

            }
        });

    }

}
