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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastViewHolder> {

    ArrayList<Cast> casts;
    Context context;

    CastAdapter(Context context,ArrayList<Cast> casts){
        this.casts=casts;
        this.context=context;
    }
    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.cast_row_layout,null,false);
        //inflate layout and return it to viewHolder
        return new CastViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull final CastViewHolder holder, int position) {

        final Cast cast = casts.get(position);
        holder.name.setText(cast.getName());
        holder.character.setText(cast.getCharacter());
        Picasso.get().load(Constants.imageURL2+cast.getProfilePath()).into(holder.image);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,CastDetail.class);
                intent.putExtra(Constants.CAST_ID,cast.getId());
                ActivityOptionsCompat options= ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,holder.image, ViewCompat.getTransitionName(holder.image));
                context.startActivity(intent,options.toBundle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return casts.size();
    }
}
