package com.example.aditi.imdb;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CastViewHolder extends RecyclerView.ViewHolder{

    ImageView image;
    TextView name;
    TextView character;
    CardView cardView;


    public CastViewHolder(View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.castImage);
        name=itemView.findViewById(R.id.castName);
        character=itemView.findViewById(R.id.characterName);
        cardView=itemView.findViewById(R.id.castCardView);
    }
}
