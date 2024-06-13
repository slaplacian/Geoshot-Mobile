package com.example.geoshot.ui.createChallenge;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.post.PostCreateChall;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateChallengeFragment extends Fragment implements OnMapReadyCallback {
    private LatLng actualLocation = new LatLng(55.6761, 12.5683);
    private Uri imageUri = null;
    private Button createchallButton;

    ActivityResultLauncher<Intent> resultLauncher;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cretate_challenge, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.id_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        createchallButton = view.findViewById(R.id.createchall_button);
        createchallButton.setEnabled(false);
        createchallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), actualLocation.latitude + "," + actualLocation.longitude, Toast.LENGTH_SHORT).show();

                String ans = actualLocation.latitude + "," + actualLocation.longitude;

                if (imageUri != null) {
                    String encodedString = convertUriToBase64(getActivity(), imageUri);
                    //Toast.makeText(getActivity(), encodedString, Toast.LENGTH_LONG).show();
                    String usernamec = SessionManager.getSession(getContext());
                    PostCreateChall.post(usernamec,encodedString,ans);

                    Toast.makeText(getActivity(), "Desafio Criado!", Toast.LENGTH_LONG).show();

                    goBackToHome();

                }
            }
        });

        Button addphotoButton = view.findViewById(R.id.addphoto_button);
        addphotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });

        registerResult();

        return view;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        MarkerOptions markerOptions = new MarkerOptions().position(this.actualLocation).title("Copem");
        Marker marker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(this.actualLocation));

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            public void onMapClick(LatLng point) {
                marker.setPosition(point);
                actualLocation = point;
            }
        });
    }

    private void pickImage() {
        Intent pickImageIntent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        resultLauncher.launch(pickImageIntent);
    }

    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        try {
                            imageUri = result.getData().getData();
                            if (imageUri != null) {
                                createchallButton.setEnabled(true);
                            }
                        } catch (Exception e) {
                            if (imageUri == null) {
                                createchallButton.setEnabled(false);
                                Toast.makeText(getActivity(), "Nenhuma imagem selecionada", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    private String convertUriToBase64(Context context, Uri uri) {
        String base64String = null;
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            byte[] bytes = getBytes(inputStream);
            base64String = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64String;
    }

    private byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void goBackToHome() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        getActivity().getSupportFragmentManager().popBackStack();
    }

}
