package com.example.geoshot.ui.search;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.geoshot.R;
import com.example.geoshot.databinding.FragmentSearchBinding;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;

public class SearchFragment extends Fragment {

    ImageView userPhotoView;
    Button searchButton, toggleButton;
    EditText searchEditText;
    TextView usernameText;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String loggedUser = SessionManager.getSession(this.getContext());
    }

}