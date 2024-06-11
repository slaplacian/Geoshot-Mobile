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

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.APIClient;
import com.example.geoshot.ui.home.utils.FeedItem;
import com.example.geoshot.ui.myChallenges.utils.MyChallengesItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyChallengesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyChallengesAdapter myChallengesAdapter;
    private final ArrayList<MyChallengesItem> myChallengesList = new ArrayList<>();

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

        myChallengesAdapter = new MyChallengesAdapter(getContext(), myChallengesList);
        recyclerView.setAdapter(myChallengesAdapter);
    }

    public void onStart() {
        super.onStart();

        APIClient api = new APIClient();
        String response = api.getRequest("xida");

        parseJson(response);
    }

    private void parseJson(String jsonText) {
        try {
            myChallengesList.clear();
            JSONObject json = new JSONObject(jsonText);

            if(json.has("publicationlist") && json.get("publicationlist") instanceof JSONArray) {
                JSONArray publicationlist = json.getJSONArray("publicationlist");

                for (int i = 0; i < publicationlist.length(); i++) {
                    JSONObject row = publicationlist.getJSONObject(i);

                    int pubId = row.getInt("pubId");
                    int ownerUserId = row.getInt("ownerUserId");
                    String photo = row.getString("photo");
                    String correctValue = row.getString("correctValue");

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
}