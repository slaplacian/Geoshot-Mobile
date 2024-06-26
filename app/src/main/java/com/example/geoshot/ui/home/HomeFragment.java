package com.example.geoshot.ui.home;

import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.get.GetInitialPage;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.home.utils.FeedItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private final ArrayList<FeedItem> feedList = new ArrayList<>();
    private TextView welcomeText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        welcomeText = (TextView) view.findViewById(R.id.welcomeUserTextHome);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        // Set layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Sending reference and data to Adapter
        homeAdapter = new HomeAdapter(getContext(), feedList, pubId -> {
            NavController navController = Navigation.findNavController(view);
            Bundle bundle = new Bundle();
            bundle.putString("pubId", pubId);
            navController.navigate(R.id.action_home_fragment_to_solveChallenge_fragment, bundle);
        });
        recyclerView.setAdapter(homeAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        String username = SessionManager.getSession(this.getContext());
        Log.d("Usuario Coletado:",username);
        String response = GetInitialPage.get(username);

        parseJson(response);
    }

    @Override
    public void onClick(View v) {
    }

    private void parseJson(String jsonText) {
//        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            feedList.clear();
            JSONObject json = new JSONObject(jsonText);
//            Log.d("Depurando", "HomeFragment -> parseJson -> Consegui transformar jsonText em json");
            if(json.has("feedlist") && json.get("feedlist") instanceof JSONArray) {
                JSONArray feedlist = json.getJSONArray("feedlist");
                if (feedlist.length() > 0) {
                    for (int i = 0; i < feedlist.length(); i++) {
//                    Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no for");
                        JSONObject row = feedlist.getJSONObject(i);
                        int pubId = row.getInt("pubId");
                        String photo = row.getString("photo");
                        String userPhoto = row.getString("userPhoto");
                        String dateOfCreation = row.getString("dateOfCreation");
                        String username = row.getString("username");


                        int insertIndex = feedList.size();
                        feedList.add(insertIndex, new FeedItem(pubId, photo, userPhoto, dateOfCreation, username));
                        Log.d("Depurando", "Dentro do for de parse json -> inserido em feedList " + feedList.size());
//                    Log.d("Depurando", "Dentro do for de parse json -> feedList " + feedList.toString());
                        homeAdapter.notifyItemInserted(insertIndex);
                    }
                }
                else {
                    String username = SessionManager.getSession(this.getContext());
                    String welcometxt = "Olá, " + username + "! Você não possui nenhuma publicação no feed ainda :(";
                    welcomeText.setText(welcometxt);
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