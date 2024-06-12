package com.example.geoshot.ui.myAttempts;

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
import com.example.geoshot.ui.myAttempts.utils.MyAttemptItem;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtils;

import java.util.ArrayList;

public class MyAttemptsAdapter extends RecyclerView.Adapter<MyAttemptsAdapter.ViewHolder> {
    Context context;
    ArrayList<MyAttemptItem> myAttemptItems;
    public MyAttemptsAdapter(Context context, ArrayList<MyAttemptItem> myAttemptItems) {
        this.context = context;
        this.myAttemptItems = myAttemptItems;
    }

    @NonNull
    @Override
    public MyAttemptsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliza o feed_item_layout.xml definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_item_layout, parent, false);

        MyAttemptsAdapter.ViewHolder viewHolder = new MyAttemptsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyAttemptsAdapter.ViewHolder holder, int position) {
        MyAttemptItem item = (MyAttemptItem) myAttemptItems.get(position);

        ImageUtils.setImageToImageView(holder.itemView, item.getPhoto(), holder.challengeImage);
        ImageUtilsPika.setImageToViewProfile(context,holder.userPhoto,item.getUserphoto());
        holder.frameUsername.setText(item.getUsername());
        holder.accuracy.setText(String.valueOf(item.getAccuracy()));
        holder.pubId.setText(String.valueOf(item.getPubId()));
    }
    @Override
    public int getItemCount() {
        return myAttemptItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView userPhoto, challengeImage;
        TextView frameUsername, accuracy, pubId;
        public ViewHolder(View view) {
            super(view);
            userPhoto = (ImageView) view.findViewById(R.id.userPhoto);
            challengeImage = (ImageView) view.findViewById(R.id.challengeImage);
            frameUsername = (TextView) view.findViewById(R.id.frameUsername);
            accuracy = (TextView) view.findViewById(R.id.criadoEm);
            pubId = (TextView) view.findViewById(R.id.pubId);
        }
    }
}
