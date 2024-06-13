package com.example.geoshot.ui.search;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.databinding.FragmentSearchBinding;
import com.example.geoshot.generalUtilities.get.GetSearch;
import com.example.geoshot.generalUtilities.put.PutToggleFollowship;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.search.utils.SearchedUser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private SearchAdapter searchAdapter;
    private ArrayList<SearchedUser> searchedUsersList = new ArrayList<>();
    String loggedUser;
    String searchedUsername;
    private EditText searchedUser;
    private Button searchUserBtn;
    private TextView searchWelcomeTextView;

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
        searchAdapter = new SearchAdapter(getContext(), searchedUsersList, this::toggleFollowship);
        recyclerView.setAdapter(searchAdapter);

        searchedUser = (EditText) view.findViewById(R.id.searchEditText);
        searchUserBtn = (Button) view.findViewById(R.id.searchButton);

        searchUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchUser();
            }
        });

        loggedUser = SessionManager.getSession(this.getContext());
        String welcomeText = "Procure seus amigos pelo username, " + loggedUser + "!";
        searchWelcomeTextView = (TextView) view.findViewById(R.id.searchWelcomeText);
        searchWelcomeTextView.setText(welcomeText);
    }

    private void parseJson(String jsonText) {
        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            searchedUsersList.clear();
            int insertIndex = searchedUsersList.size();
            searchAdapter.notifyItemRemoved(insertIndex);
            JSONObject json = new JSONObject(jsonText);
            Log.d("Depurando", "consegui pegar JSON");
            if(json.getString("status").equals("success") && json.getString("user-not-found").equals("false")){
                JSONObject user = json.getJSONObject("user");

                String followshipState = json.getString("followship-state");
                String searchedUsername = user.getString("username");
                String searchedUserPhoto = user.getString("photo");

                insertIndex = searchedUsersList.size();
                searchedUsersList.add(insertIndex, new SearchedUser(searchedUsername, searchedUserPhoto, followshipState));
                Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + searchedUsersList.size());
    //                    Log.d("Depurando", "Dentro do for de parse json -> feedList " + feedList.toString());
                Log.d("Depurando", "Estou antes de Notify");
                searchAdapter.notifyItemInserted(insertIndex);
                Log.d("Depurando", "Estou depois de Notify");
            }
        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void searchUser() {
        searchedUsername = searchedUser.getText().toString();
        String response = GetSearch.get(loggedUser, searchedUsername);
        Log.d("serserser",response);
        parseJson(response);
    }

    private void toggleFollowship(String searchedUsername){
        String response = PutToggleFollowship.put(this.loggedUser, searchedUsername);
    }

}