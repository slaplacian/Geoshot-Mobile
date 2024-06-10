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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.geoshot.R;
import com.example.geoshot.databinding.FragmentHomeBinding;
import com.example.geoshot.ui.home.utils.FeedItem;
import com.example.geoshot.utils.APIClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Adapter adapter;
    private final ArrayList<FeedItem> feedList = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy .Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        onClick(this.getView());

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onClick(View v) {
        String loggedUser = getArguments().getString("loggedUser");

        APIClient api = new APIClient();
        String response = api.getRequest(loggedUser);

        parseJson(response);
    }

    private void parseJson(String jsonText ) {
        try {
            JSONObject json = new JSONObject(jsonText);

            if (json.getString("status").equals("success")) {
                JSONArray feedlist = json.getJSONArray("feedlist");

                for (int i = 0; i < feedlist.length(); i++) {
                    JSONObject row = feedlist.getJSONObject(i);
                    String pubId = row.getString("pubId");
                    String photo = row.getString("photo");
                    String userPhoto = row.getString("userPhoto");
                    String dateOfCreation = row.getString("dateOfCreation");
                    String username = row.getString("username");

                    int intpubId = Integer.parseInt(pubId);

                    int insertIndex = feedList.size();
                    feedList.add(insertIndex, new FeedItem(intpubId, photo, userPhoto, dateOfCreation, username));
                    adapter.notifyItemInserted(insertIndex);
                }
            }
        } catch (JSONException e ) {
            Log.d("Depurando", "Erro em parseJson -> BaseActivity -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (NumberFormatException e) {
            Log.d("Depurando", "Erro em parseJson -> BaseActivity -> NumberFormatException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}