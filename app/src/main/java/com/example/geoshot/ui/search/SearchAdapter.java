package com.example.geoshot.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtils;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtilsPika;
import com.example.geoshot.ui.search.utils.SearchedUser;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    Context context;
    ArrayList<SearchedUser> searchedUsersList;
    public SearchAdapter(Context context, ArrayList<SearchedUser> searchedUsersList) {
        this.context = context;
        this.searchedUsersList = searchedUsersList;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Utiliza o feed_item_layout.xml definido
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.searched_friend, parent, false);

        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        SearchedUser item = (SearchedUser) searchedUsersList.get(position);

        ImageUtilsPika.setImageToViewProfile(context, holder.searchedUserPhoto, item.getPhoto());
        holder.searchedUsername.setText(item.getUsername());
        holder.btnToggleFollowShip.setText(item.getFollowshipState());
    }

    @Override
    public int getItemCount() {
        return searchedUsersList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView searchedUserPhoto;
        TextView searchedUsername;
        Button btnToggleFollowShip;
        public ViewHolder(View view) {
            super(view);
            searchedUserPhoto = (ImageView) view.findViewById(R.id.searchedUserPhoto);
            searchedUsername = (TextView) view.findViewById(R.id.searchedUsername);
            btnToggleFollowShip = (Button) view.findViewById(R.id.btnToggleFollowShip);
        }
    }
}
