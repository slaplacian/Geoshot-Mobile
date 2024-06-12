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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_attempts, container, false);
    }

    public void onCreatedView(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.myAttemptsRecyclerView);
        // Set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        myAttemptsAdapter = new MyAttemptsAdapter(getContext(), myAttemptsList);
        recyclerView.setAdapter(myAttemptsAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = SessionManager.getSession(this.getContext());
        String response = GetMyAttempts.get(username);

        parseJson(response);
    }

    private void parseJson(String jsonText) {
//        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            myAttemptsList.clear();
            JSONObject json = new JSONObject(jsonText);
//            Log.d("Depurando", "HomeFragment -> parseJson -> Consegui transformar jsonText em json");
            if(json.has("attemptslist") && json.get("attemptslist") instanceof JSONArray) {
                JSONArray feedlist = json.getJSONArray("attemptslist");
                for (int i = 0; i < feedlist.length(); i++) {
//                    Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no for");
                    JSONObject row = feedlist.getJSONObject(i);

                    int pubId = row.getInt("pubId");
                    double accuracy = row.getDouble("accuracy");
                    String username = row.getString("username");
                    String photo = row.getString("photo");
                    String userphoto = row.getString("userphoto");
                    String attemptDate = row.getString("attemptDate");


                    int insertIndex = myAttemptsList.size();
                    myAttemptsList.add(insertIndex, new MyAttemptItem(pubId, accuracy, username,photo, userphoto, attemptDate));
                    Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + myAttemptsList.size());
//                    Log.d("Depurando", "Dentro do for de parse json -> feedList " + feedList.toString());
                    myAttemptsAdapter.notifyItemInserted(insertIndex);
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