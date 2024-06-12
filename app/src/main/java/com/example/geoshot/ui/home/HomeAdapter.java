package com.example.geoshot.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtilsPika;
import com.example.geoshot.ui.home.utils.FeedItem;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtils;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    Context context;
    ArrayList<FeedItem> feedItems;
    public HomeAdapter(Context context, ArrayList<FeedItem> feedItems) {
        this.context = context;
        this.feedItems = feedItems;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliza o feed_item_layout.xml definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_layout, parent, false);

        HomeAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        FeedItem item = (FeedItem) feedItems.get(position);

        ImageUtilsPika piker = new ImageUtilsPika();
        piker.setImageToView(context,holder.challengeImage,item.getPhoto());
        piker.setImageToViewProfile(context,holder.userPhoto,item.getUserPhoto());
        //ImageUtils.setImageToImageView(holder.itemView, item.getPhoto(), holder.challengeImage);
        //ImageUtils.setImageToImageView(holder.itemView, item.getUserPhoto(), holder.userPhoto);
        holder.frameUsername.setText(item.getUsername());
        holder.criadoEm.setText(item.getDateOfCreation());
        holder.pubId.setText(String.valueOf(item.getPubId()));
    }
    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto, challengeImage;
        TextView frameUsername, criadoEm, pubId;
        public ViewHolder(View view) {
            super(view);
            userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
            challengeImage = (ImageView) view.findViewById(R.id.challengeImage);
            frameUsername = (TextView) view.findViewById(R.id.frameUsername);
            criadoEm = (TextView) view.findViewById(R.id.criadoEm);
            pubId = (TextView) view.findViewById(R.id.pubId);
        }
    }
}
