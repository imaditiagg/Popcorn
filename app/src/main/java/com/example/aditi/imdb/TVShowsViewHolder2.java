package com.example.aditi.imdb;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TVShowsViewHolder2 extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView titleView;
    View itemView;
    final Context context;
    Button favButton;
    CardView cardView;

    public TVShowsViewHolder2(View itemView,Context context) {
        super(itemView);
        this.itemView=itemView;
        this.context=context;
        imageView=itemView.findViewById(R.id.imageView2);
        titleView=itemView.findViewById(R.id.titleView2);
        cardView=itemView.findViewById(R.id.card_view_show_card);
        favButton=itemView.findViewById(R.id.image_fav_button2);
        imageView.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.31);
        imageView.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

    }
}
