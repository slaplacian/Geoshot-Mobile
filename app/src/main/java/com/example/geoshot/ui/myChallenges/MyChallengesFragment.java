package com.example.geoshot.ui.myChallenges;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.delete.DeleteMyChalls;
import com.example.geoshot.generalUtilities.get.GetMyChalls;
import com.example.geoshot.generalUtilities.put.PutToggleFollowship;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.myChallenges.utils.MyChallengesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyChallengesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyChallengesAdapter myChallengesAdapter;
    private final ArrayList<MyChallengesItem> myChallengesList = new ArrayList<>();
    String username;
    private TextView noPubsText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_challenges, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.myChallengesRecyclerView);
        // Set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myChallengesAdapter = new MyChallengesAdapter(getContext(), myChallengesList, this::deletePublication);
        recyclerView.setAdapter(myChallengesAdapter);

        noPubsText = (TextView) view.findViewById(R.id.noPubsText);
    }

    public void onStart() {
        super.onStart();

        username = SessionManager.getSession(this.getContext());
        String response = GetMyChalls.get(username);

        parseJson(response);
    }

    private void parseJson(String jsonText) {
        try {
            myChallengesList.clear();
            JSONObject json = new JSONObject(jsonText);

            if(json.has("publicationlist") && json.get("publicationlist") instanceof JSONArray) {
                JSONArray publicationlist = json.getJSONArray("publicationlist");
                if (publicationlist.length() == 0) {
                    noPubsText.setText("Você ainda não desafiou ninguém!");
                }
                for (int i = 0; i < publicationlist.length(); i++) {
                    JSONObject row = publicationlist.getJSONObject(i);

                    int pubId = row.getInt("pubId");
                    int ownerUserId = row.getInt("ownerUserId");
                    String photo = row.getString("photo");
                    String correctValue = row.getString("correctValue");

                    String[] values = correctValue.split(",");

                    correctValue = String.format("Lat:%s\nLong:%s",values[0],values[1]);

                    int insertIndex = myChallengesList.size();
                    myChallengesList.add(insertIndex, new MyChallengesItem(pubId, ownerUserId, photo, correctValue));
                    Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + myChallengesList.size());
                    myChallengesAdapter.notifyItemInserted(insertIndex);
                }
            } else {
                Log.d("Depurando", "HomeFragment -> parseJson -> 'feedlist' não é um array ou não existe");
            }
        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void deletePublication(String pubId){
        String response = DeleteMyChalls.delete(this.username, pubId);
        deletePublicationInList(pubId);
    }

    private void deletePublicationInList(String pubId){
        for(int i = 0; i < myChallengesList.size(); i++){
            if(myChallengesList.get(i).getPubId() == Integer.parseInt(pubId)){
                myChallengesList.remove(i);
                myChallengesAdapter.notifyItemRemoved(i);
            }
        }
    }
}