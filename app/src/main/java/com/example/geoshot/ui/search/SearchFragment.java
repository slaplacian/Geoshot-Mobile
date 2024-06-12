package com.example.geoshot.ui.search;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.databinding.FragmentSearchBinding;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.search.utils.SearchedUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private final ArrayList<SearchedUser> searchedUsersList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        recyclerView = (RecyclerView) view.findViewById(R.id.searchRecyclerView);
        // Set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Sending reference and data to Adapter
        searchAdapter = new SearchAdapter(getContext(), searchedUsersList);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

        

    }

    private void parseJson(String jsonText) {
//        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            searchedUsersList.clear();
            JSONObject json = new JSONObject(jsonText);
            JSONObject user = json.getJSONObject("user");

            String followshipState = json.getString("followship-state");
            String searchedUsername = user.getString("username");
            String searchedUserPhoto = user.getString("photo");

            int insertIndex = searchedUsersList.size();
            searchedUsersList.add(insertIndex, new SearchedUser(searchedUsername, searchedUserPhoto, followshipState));
            Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + searchedUsersList.size());
//                    Log.d("Depurando", "Dentro do for de parse json -> feedList " + feedList.toString());
            searchAdapter.notifyItemInserted(insertIndex);


        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}