package com.example.aditi.imdb;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TVCastOfPersonAdapter  extends RecyclerView.Adapter<TVCastOfPersonAdapter.TvViewHolder>{

    private Context context;
    private ArrayList<TVCast> casts;

    public TVCastOfPersonAdapter(Context context, ArrayList<TVCast> casts) {
        this.context = context;
        this.casts = casts;
    }

    @Override
    public TVCastOfPersonAdapter.TvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TVCastOfPersonAdapter.TvViewHolder(LayoutInflater.from(context).inflate(R.layout.cast_work_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(TVCastOfPersonAdapter.TvViewHolder holder, int position) {
        if (casts.get(position).getName() != null)
            holder.movieTitleTextView.setText(casts.get(position).getName());
        else
            holder.movieTitleTextView.setText("");

        if (casts.get(position).getCharacter() != null && !casts.get(position).getCharacter().trim().isEmpty())
            holder.castCharacterTextView.setText("as " + casts.get(position).getCharacter());
        else
            holder.castCharacterTextView.setText("");

        Picasso.get().load(Constants.imageURL2+casts.get(position).getPoster_path()).into(holder.moviePosterImageView);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {

        public CardView movieCard;
        public ImageView moviePosterImageView;
        public TextView movieTitleTextView;
        public TextView castCharacterTextView;

        public TvViewHolder(View itemView) {
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
                   /* Intent intent = new Intent(context, MovieDetail.class);
                    intent.putExtra(Constants.ID, casts.get(getAdapterPosition()).getId());
                    context.startActivity(intent);*/
                }
            });
        }
    }
}
