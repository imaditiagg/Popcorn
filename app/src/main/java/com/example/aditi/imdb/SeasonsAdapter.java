package com.example.aditi.imdb;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.crypto.AEADBadTagException;

public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Season> seasons;

    public SeasonsAdapter(Context mContext, ArrayList<Season> s) {
        this.context = mContext;
        this.seasons=s;
    }

    @NonNull
    @Override
    public SeasonsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SeasonsAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.season_item_layout, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SeasonsAdapter.ViewHolder holder, int position) {
        Season season=seasons.get(position);
        holder.tv1.setText(season.getName());
        if(!season.getAir_date().isEmpty()) {
            holder.tv2.setText("Air Date: " + season.getAir_date());
        }
        if(!season.getEpisodes().isEmpty()) {
            holder.tv3.setText(season.getEpisodes().size() + " Episodes");
        }

    }

    @Override
    public int getItemCount() {
        return seasons.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv1,tv2,tv3;
        public ViewHolder(View view){
            super(view);
            tv1=view.findViewById(R.id.seasonName);
            tv2=view.findViewById(R.id.seasonAirDate);
            tv3=view.findViewById(R.id.seasonEpisodes);
        }
    }
}
