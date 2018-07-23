package com.example.aditi.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewsAdapter extends ArrayAdapter {
    ArrayList<Review> reviews;
    Context mContext;
    View view;
    LayoutInflater inflater;
    public ReviewsAdapter(@NonNull Context context, ArrayList<Review> reviews) {
        super(context, 0,reviews);
        this.reviews=reviews;
        mContext=context;
        inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

       view=convertView;
       if(view==null){
           view = inflater.inflate(R.layout.review_row_layout, parent, false);
           TextView t1= view.findViewById(R.id.userName);
           TextView t2= view.findViewById(R.id.userReview);
           ReviewHolder holder = new ReviewHolder();
           holder.name= t1;
           holder.review=t2;
           view.setTag(holder);
       }
       final Review review = reviews.get(position);
       ReviewHolder holder= (ReviewHolder) view.getTag();
       holder.name.setText(review.getAuthor()); //set author name

       int length = review.getContent().length();
       SpannableString text = new SpannableString(" .... Read More");
       if(length>60 && (Math.abs(length-60))>10){
           SpannableStringBuilder spannableStringBuilder=new SpannableStringBuilder(review.getContent(),0,45);
           spannableStringBuilder.append(text);
           holder.review.setText(spannableStringBuilder.toString());
       }
       else {
           holder.review.setText(review.getContent()); //set content of review
       }


       return view;
    }
}
