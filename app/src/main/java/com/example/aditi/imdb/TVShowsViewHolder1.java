package com.example.aditi.imdb;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TVShowsViewHolder1 extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView titleView,ratingsView;
    View itemView;
    RelativeLayout imageLayout;
    CardView cardView;
    Context context;
    Button favButton;

    public TVShowsViewHolder1(View itemView,Context context) {
        super(itemView);
        this.context=context;
        this.itemView=itemView;
        imageView=itemView.findViewById(R.id.imageView);
        titleView=itemView.findViewById(R.id.titleView);
        ratingsView=itemView.findViewById(R.id.ratingsView);
        cardView=itemView.findViewById(R.id.card_view);
        imageLayout=itemView.findViewById(R.id.image_layout_show_card);
        favButton=itemView.findViewById(R.id.image_fav_button);
        imageLayout.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.9);
        imageLayout.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.9) / 1.77);
    }
}
