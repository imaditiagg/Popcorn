package com.example.aditi.imdb;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<SearchResult> results;

    SearchAdapter(Context context,ArrayList<SearchResult> results){
        this.context=context;
        this.results=results;
    }


    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowLayout = inflater.inflate(R.layout.search_item_row_layout, null, false);
        //inflate layout and return it to viewHolder
        return new SearchViewHolder(rowLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {

        //obtain and set Data
        final SearchResult result = results.get(position);
        if(result.getMedia_type().equals("movie")) {
            if (result.getTitle() != null) {
                holder.tv1.setText(result.getTitle());
            } else
                holder.tv1.setText("");

            if (result.getMedia_type() != null) {
                holder.tv2.setText("Movie");
            } else
                holder.tv2.setText("");

            if (result.overview != null) {
                holder.tv3.setText(result.overview);
            } else
                holder.tv3.setText("");

            if (result.getRelease_date() != null) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                try {
                    Date releaseDate = sdf1.parse(result.getRelease_date());
                    holder.tv4.setText(sdf2.format(releaseDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                holder.tv4.setText("");

            }
            Picasso.get().load(Constants.imageURL+result.getPoster_path()).into(holder.imageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,MovieDetail.class);
                    intent.putExtra(Constants.ID,result.getId());
                    intent.putExtra(Constants.TYPE,Constants.MOVIETYPE);
                    context.startActivity(intent);

                }
            });
        }
        else if(result.getMedia_type().equals("tv")) {
            if (result.getName() != null) {
                holder.tv1.setText(result.getName());
            } else
                holder.tv1.setText("");

            if (result.getMedia_type() != null) {
                holder.tv2.setText("TV");
            } else
                holder.tv2.setText("");

            if (result.overview != null) {
                holder.tv3.setText(result.overview);
            } else
                holder.tv3.setText("");

            if (result.getFirst_air_date() != null) {
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy");
                try {
                    Date releaseDate = sdf1.parse(result.getFirst_air_date());
                    holder.tv4.setText(sdf2.format(releaseDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                holder.tv4.setText("");

            }
            Picasso.get().load(Constants.imageURL+result.getPoster_path()).into(holder.imageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,TVShowDetail.class);
                    intent.putExtra(Constants.ID,result.getId());
                    intent.putExtra(Constants.TYPE,Constants.TVTYPE);
                    context.startActivity(intent);

                }
            });
        }

        else if(result.getMedia_type().equals("person")) {
            if (result.getName() != null) {
                holder.tv1.setText(result.getName());
            } else
                holder.tv1.setText("");

            if (result.getMedia_type() != null) {
                holder.tv2.setText("Person");
            } else
                holder.tv2.setText("");

            holder.tv3.setText("");
            holder.tv4.setText("");
            Picasso.get().load(Constants.imageURL+result.getProfile_path()).into(holder.imageView);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(context,CastDetail.class);
                    intent.putExtra(Constants.CAST_ID,(long)result.getId());
                    context.startActivity(intent);

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return results.size();
    }





    public class SearchViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView tv1;
        TextView tv2;
        TextView tv3;
        TextView tv4;
        CardView cardView;

        public SearchViewHolder(View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.search_imageView);
            tv1= itemView.findViewById(R.id.search_text_view_name );
            tv2= itemView.findViewById(R.id.search_text_view_media_type);
            tv3= itemView.findViewById(R.id.search_text_view_overview);
            tv4=itemView.findViewById(R.id.search_text_view_year);
            cardView=itemView.findViewById(R.id.search_card);

        }
    }
}
