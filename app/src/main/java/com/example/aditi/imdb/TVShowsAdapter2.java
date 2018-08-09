package com.example.aditi.imdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVShowsAdapter2 extends RecyclerView.Adapter<TVShowsViewHolder2 >{

    Context context;
    ArrayList<TV> shows;
    TVShowsAdapter2(Context context,ArrayList<TV> shows){
        this.context=context;
        this.shows=shows;
    }

    @NonNull
    @Override
    public TVShowsViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.movie_row_layout2,null,false);
        //inflate layout and return it to viewHolder
        return new TVShowsViewHolder2(rowLayout,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final TVShowsViewHolder2 holder, int i) {
        //Set data
        final TV show = shows.get(i);
        holder.titleView.setText(show.name);

        Picasso.get().load(Constants.imageURL2+show.poster_path).into(holder.imageView);
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
                ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.imageView, ViewCompat.getTransitionName(holder.imageView));
                context.startActivity(intent,options.toBundle());
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
