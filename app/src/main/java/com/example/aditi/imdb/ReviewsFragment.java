package com.example.aditi.imdb;


import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    TextView tv;

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

        Bundle bundle=getArguments();
        movieId= bundle.getInt(Constants.ID);

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
                TextView name = v.findViewById(R.id.userName);
                TextView content =v.findViewById(R.id.userReview);
                name.setText(review.getAuthor());
                content.setText(review.getContent());

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        fetchReviews();
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
                    fetchReviews();

            }
        });

        return view;
    }


    public void fetchReviews(){
        if(currentPage==1) {
            progressBar.setVisibility(View.VISIBLE);
            reviewsView.setVisibility(View.GONE);
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
                        progressBar.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.VISIBLE);
                    }

                }
                else{
                    if(currentPage==1){

                        progressBar.setVisibility(View.GONE);
                        reviewsView.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(),"No Reviews",Toast.LENGTH_SHORT).show();
                        tv.setVisibility(View.GONE);
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
