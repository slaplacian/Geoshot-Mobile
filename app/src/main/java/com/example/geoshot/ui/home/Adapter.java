package com.example.geoshot.ui.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.ui.home.utils.FeedItem;
import com.example.geoshot.ui.home.utils.ImageUtils;

import java.util.ArrayList;

import coil.Coil;
import coil.ImageLoader;
import coil.request.ImageRequest;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<FeedItem> feedItems;
    public Adapter(Context context, ArrayList<FeedItem> feedItems) {
        this.context = context;
        this.feedItems = feedItems;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliza o feed_item_layout.xml definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
        FeedItem item = (FeedItem) feedItems.get(position);

        ImageUtils.setImageToImageView(holder, item.getPhoto(), holder.challengeImage);
        ImageUtils.setImageToImageView(holder, item.getUserPhoto(), holder.userPhoto);
        holder.username.setText(item.getUsername());
        holder.criadoEm.setText(item.getDateOfCreation());
        holder.pubId.setText(item.getPubId());
    }
    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto, challengeImage;
        TextView username, criadoEm, pubId;
        public ViewHolder(View view) {
            super(view);
            userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
            challengeImage = (ImageView) view.findViewById(R.id.challengeImage);
            username = (TextView) view.findViewById(R.id.username);
            criadoEm = (TextView) view.findViewById(R.id.criadoEm);
            pubId = (TextView) view.findViewById(R.id.pubId);
        }
    }
}
