package com.example.aditi.imdb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieAdapter1 extends RecyclerView.Adapter<MovieViewHolder1> {

    ArrayList<Movie> movies;
    Context context;

    MovieAdapter1(Context context,ArrayList<Movie> movies){
        this.context=context;
        this.movies=movies;

    }

    @NonNull
    @Override
    public MovieViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.movie_row_layout,null,false);
        //inflate layout and return it to viewHolder
        return new MovieViewHolder1(rowLayout,context);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder1 holder, int i) {
        //Set data
        final Movie movie = movies.get(i);
        holder.titleView.setText(movie.title);
        holder.ratingsView.setText(String.format("%.1f", movie.rating) + "★");
        Picasso.get().load(Constants.imageURL+movie.backdropPath).into(holder.imageView);
        holder.cardView.setCardBackgroundColor(context.getResources().getColor(R.color.black));
        if (Favorite.isMovieFav(context, movie.getId())) {
            holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
            holder.favButton.setEnabled(true);
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
                context.startActivity(intent);
            }
        });

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Favorite.isMovieFav(context,movie.getId())) {
                    Favorite.addMovieToFav(context, movie);
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_red_600_24dp));
                    Toast.makeText(context, "Movie added to Favorites", Toast.LENGTH_SHORT).show();
                }
                else{
                    Favorite.removeMovieFromFav(context,movie.id);
                    holder.favButton.setBackground(context.getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                    Toast.makeText(context, "Movie removed from Favorites", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}
