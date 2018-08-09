package com.example.aditi.imdb;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter2 extends RecyclerView.Adapter<MovieViewHolder2> {

    ArrayList<Movie> movies;
    Context context;

    MovieAdapter2(Context context,ArrayList<Movie> movies){
        this.context=context;
        this.movies=movies;

    }

    @NonNull
    @Override
    public MovieViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.movie_row_layout2,null,false);
        //inflate layout and return it to viewHolder
        return new MovieViewHolder2(rowLayout,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder2 holder, int i) {
        //Set data
        final Movie movie = movies.get(i);
        holder.titleView.setText(movie.title);
        Picasso.get().load(Constants.imageURL2+movie.posterPath).into(holder.imageView);
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.black));
        if (Favorite.isMovieFav(context, movie.getId())) {
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
                Intent intent = new Intent(context, MovieDetail.class);
                intent.putExtra(Constants.ID, movie.id);
                intent.putExtra(Constants.TYPE,Constants.MOVIETYPE);
                ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.imageView, ViewCompat.getTransitionName(holder.imageView));
                context.startActivity(intent,options.toBundle());
            }
        });

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Favorite.isMovieFav(context,movie.getId())) {
                    //add to db
                    Favorite.addMovieToFav(context, movie);
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    holder.favButton.setEnabled(false);
                }
                else{
                    //means already added to db just change the background
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(context, "Added to Favorites", Toast.LENGTH_SHORT).show();
                    holder.favButton.setEnabled(false);

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}