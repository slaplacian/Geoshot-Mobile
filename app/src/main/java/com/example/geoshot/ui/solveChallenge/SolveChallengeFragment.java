package com.example.geoshot.ui.solveChallenge;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.post.PostChall;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.example.geoshot.ui.home.HomeFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class SolveChallengeFragment extends Fragment implements OnMapReadyCallback {
    private LatLng actualLocation = new LatLng(55.6761, 12.5683);
    private SolveChallengeViewModel mViewModel;

    private String pubId;

    public static SolveChallengeFragment newInstance() {
        return new SolveChallengeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_solve_challenge, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        Button findyouButton = rootView.findViewById(R.id.findyou_button);
        findyouButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),actualLocation.latitude + ", " +actualLocation.longitude, Toast.LENGTH_SHORT).show();
                String userAnswer = actualLocation.latitude + "," +actualLocation.longitude;
                String username = SessionManager.getSession(getContext());
                TextView pubIdSolveChallenge = v.findViewById(R.id.pubIdSolveChallenge);
                Log.d("DEPURANDO","Cheguei aqui");

                String response = PostChall.post(username,pubId,userAnswer);

                Log.d("DEPURANDO",response);

                goBackToHome();

            }
        });

        return rootView;
    }

    private void goBackToHome() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.container, new HomeFragment())
//                .addToBackStack(null).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView textView = view.findViewById(R.id.pubIdSolveChallenge);
        if (getArguments() != null) {
            String selectedItem = getArguments().getString("pubId");
            pubId = selectedItem;
            textView.setText(selectedItem);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions().position(actualLocation).title("Copem");
        Marker marker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(actualLocation));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            public void onMapClick(LatLng point){
                marker.setPosition(point);
                actualLocation = point;
            }
        });
    }
}
