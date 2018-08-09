package com.example.aditi.imdb;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context context;
    private List<Video> videos;

    public VideoAdapter(Context mContext, List<Video> videos) {
        this.context = mContext;
        this.videos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {

        Picasso.get().load(Constants.YOUTUBE_THUMBNAIL_BASE_URL + videos.get(position).getKey() + Constants.YOUTUBE_THUMBNAIL_IMAGE_QUALITY)
                .into(holder.videoImageView);

        if (videos.get(position).getName() != null)
            holder.videoTextView.setText(videos.get(position).getName());
        else
            holder.videoTextView.setText("");
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        public CardView videoCard;
        public ImageView videoImageView;
        public TextView videoTextView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            videoCard =  itemView.findViewById(R.id.card_view_video);
            videoImageView = itemView.findViewById(R.id.image_view_video);
            videoTextView =  itemView.findViewById(R.id.text_view_video_name);

            videoCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.YOUTUBE_WATCH_BASE_URL + videos.get(getAdapterPosition()).getKey()));
                    context.startActivity(youtubeIntent);
                }
            });
        }
    }


}

