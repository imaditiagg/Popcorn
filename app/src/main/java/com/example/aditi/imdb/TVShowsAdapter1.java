package com.example.aditi.imdb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVShowsAdapter1 extends RecyclerView.Adapter<TVShowsViewHolder1> {

    Context context;
    ArrayList<TV> shows;

    TVShowsAdapter1(Context context,ArrayList<TV> shows){
        this.context=context;
        this.shows=shows;
    }
    @NonNull
    @Override
    public TVShowsViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.movie_row_layout,null,false);
        //inflate layout and return it to viewHolder
        return new TVShowsViewHolder1(rowLayout,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVShowsViewHolder1 holder, int i) {

        //Set data
        final TV show = shows.get(i);
        holder.titleView.setText(show.name);
        holder.ratingsView.setText(String.format("%.1f", show.vote_average) + "★");
        Picasso.get().load(Constants.imageURL+show.backdrop_path).into(holder.imageView);
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.black));
        if (Favorite.isShowFav(context, show.id)) {
            holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
            holder.favButton.setEnabled(false);
        } else {
            holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
            holder.favButton.setEnabled(true);
        }


        //set onClickListener
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TVShowDetail.class);
                intent.putExtra(Constants.ID, show.id);
                intent.putExtra(Constants.TYPE,Constants.TVTYPE);
                context.startActivity(intent);
            }
        });

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Favorite.isShowFav(context,show.getId())) {
                    //add to db
                    Favorite.addShow(context, show);
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    holder.favButton.setEnabled(false);
                }
                else{
                    // means already added to db just change the background
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    holder.favButton.setEnabled(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }
}
