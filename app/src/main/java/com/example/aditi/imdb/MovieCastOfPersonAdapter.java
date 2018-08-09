package com.example.aditi.imdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieCastOfPersonAdapter extends RecyclerView.Adapter<MovieCastOfPersonAdapter.MovieViewHolder>{

    private Context context;
    private ArrayList<MovieCast> movieCasts;

    public MovieCastOfPersonAdapter(Context context, ArrayList<MovieCast> movies) {
        this.context = context;
        movieCasts = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_work_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        if (movieCasts.get(position).title != null)
            holder.movieTitleTextView.setText(movieCasts.get(position).title);
        else
            holder.movieTitleTextView.setText("");

        if (movieCasts.get(position).character != null && !movieCasts.get(position).character.trim().isEmpty())
            holder.castCharacterTextView.setText("as " + movieCasts.get(position).character);
        else
            holder.castCharacterTextView.setText("");

        Picasso.get().load(Constants.imageURL2+movieCasts.get(position).poster_path).into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return movieCasts.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public CardView movieCard;
        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;
        public TextView castCharacterTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieCard = (CardView) itemView.findViewById(R.id.cast_item_card_view);
            moviePosterImageView = (ImageView) itemView.findViewById(R.id.itemImage);
            movieTitleTextView = (TextView) itemView.findViewById(R.id.itemTitle);
            castCharacterTextView = (TextView) itemView.findViewById(R.id.itemCharacter);

            moviePosterImageView.getLayoutParams().width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.31);
            moviePosterImageView.getLayoutParams().height = (int) ((context.getResources().getDisplayMetrics().widthPixels * 0.31) / 0.66);

            movieCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtra(Constants.ID, movieCasts.get(getAdapterPosition()).id);
                    ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,moviePosterImageView, ViewCompat.getTransitionName(moviePosterImageView));
                    context.startActivity(intent,options.toBundle());
                }
            });
        }
    }
}
