package com.example.geoshot.ui.myAttempts;

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
import com.example.geoshot.generalUtilities.get.GetMyAttempts;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.myAttempts.utils.MyAttemptItem;
import com.example.geoshot.generalUtilities.APIClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAttemptsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MyAttemptsAdapter myAttemptsAdapter;
    private final ArrayList<MyAttemptItem> myAttemptsList = new ArrayList<>();
    private TextView noMyAttempts;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_attempts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.myAttemptsRecyclerView);
        // Set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myAttemptsAdapter = new MyAttemptsAdapter(getContext(), myAttemptsList);
        recyclerView.setAdapter(myAttemptsAdapter);

        noMyAttempts = (TextView) view.findViewById(R.id.noMyAttempts);
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = SessionManager.getSession(this.getContext());
        String response = GetMyAttempts.get(username);

        JSONObject json = null;
        try {
            json = new JSONObject(response);

            if(json.has("attemptslist") && json.get("attemptslist") instanceof JSONArray) {
                JSONArray feedlist = json.getJSONArray("attemptslist");
                if (feedlist.length() > 0) {
                    parseJson(response);
                    recyclerView.setVisibility(View.VISIBLE);
                    noMyAttempts.setVisibility(View.GONE);
                }
                else {
                    recyclerView.setVisibility(View.GONE);
                    noMyAttempts.setVisibility(View.VISIBLE);
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseJson(String jsonText) {
        try {
            myAttemptsList.clear();
            JSONObject json = new JSONObject(jsonText);

            JSONArray feedlist = json.getJSONArray("attemptslist");

                for (int i = 0; i < feedlist.length(); i++) {
                    JSONObject row = feedlist.getJSONObject(i);

                    int pubId = row.getInt("pubId");
                    double accuracy = row.getDouble("accuracy");
                    String username = row.getString("username");
                    String photo = row.getString("photo");
                    String userphoto = row.getString("userphoto");
                    String attemptDate = row.getString("attemptDate");

                    int insertIndex = myAttemptsList.size();
                    myAttemptsList.add(insertIndex, new MyAttemptItem(pubId, accuracy, username, photo, userphoto, attemptDate));
                    Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + myAttemptsList.size());
                    //                    Log.d("Depurando", "Dentro do for de parse json -> feedList " + feedList.toString());
                    myAttemptsAdapter.notifyItemInserted(insertIndex);
                }


        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}