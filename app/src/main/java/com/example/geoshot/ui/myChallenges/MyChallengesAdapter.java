package com.example.geoshot.ui.myChallenges;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtils;
import com.example.geoshot.ui.myChallenges.utils.MyChallengesItem;

import java.util.ArrayList;

public class MyChallengesAdapter extends RecyclerView.Adapter<MyChallengesAdapter.ViewHolder>{
    Context context;
    ArrayList<MyChallengesItem> myChallengesList;
    public MyChallengesAdapter(Context context, ArrayList<MyChallengesItem> myChallengesList) {
        this.context = context;
        this.myChallengesList = myChallengesList;
    }

    @NonNull
    @Override
    public MyChallengesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliza o feed_my_challenges_item.xml definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_my_challenges_item, parent, false);

        MyChallengesAdapter.ViewHolder viewHolder = new MyChallengesAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyChallengesAdapter.ViewHolder holder, int position) {
        MyChallengesItem item = (MyChallengesItem) myChallengesList.get(position);

        ImageUtils.setImageToImageView(holder.itemView, item.getPhoto(), holder.myChallengesImage);
        holder.correctValue.setText(String.valueOf(item.getCorrectValue()));
        holder.pubId.setText(String.valueOf(item.getPubId()));
    }

    public int getItemCount() {
        return myChallengesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView myChallengesImage;
        TextView correctValue, pubId;
        public ViewHolder(View view) {
            super(view);

            myChallengesImage = (ImageView) view.findViewById(R.id.myChallengesImage);
            correctValue = (TextView) view.findViewById(R.id.correctValue);
            pubId = (TextView) view.findViewById(R.id.pubId);
        }
    }
}
