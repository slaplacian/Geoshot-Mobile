package com.example.geoshot.ui.perfil;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.geoshot.R;
import com.example.geoshot.generalUtilities.get.GetPerfil;
import com.example.geoshot.generalUtilities.imageUtils.ImageUtilsPika;
import com.example.geoshot.generalUtilities.sqlite.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;

public class PerfilFragment extends Fragment {

    TextView accuracyProfile, usernameProfile;
    ImageView userPhoto;
    double accuracy;
    String encodedString;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        usernameProfile = view.findViewById(R.id.usernameProfile);

        accuracyProfile = view.findViewById(R.id.user_score);

        userPhoto = view.findViewById(R.id.profile_image);

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Button editProfileButton = view.findViewById(R.id.edit_profile_button);
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_perfil_to_editProfile);
            }

        });

        Button myChallengesButton = view.findViewById(R.id.my_challenges_button);
        myChallengesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_perfil_fragment_to_myChallenges_fragment);
            }
        });

        Button solvedByMeButton = view.findViewById(R.id.solved_by_me_button);
        solvedByMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.container)
                        .navigate(R.id.action_perfil_fragment_to_solvedByMe_fragment);
            }
        });
        Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { requireActivity().finish(); }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        String username = SessionManager.getSession(this.getContext());
        String response = GetPerfil.get(username);

        parseJson(response);

        usernameProfile.setText(username);
        accuracyProfile.setText(String.format("%.1f",accuracy)+"%");
        ImageUtilsPika.setImageToViewProfile(getContext(),userPhoto,encodedString);

    }

    private void parseJson(String jsonText) {
//        Log.d("Depurando", "HomeFragment -> parseJson -> Entrei no parseJson");
        try {
            JSONObject json = new JSONObject(jsonText);
            JSONObject user = json.getJSONObject("user");

           accuracy = user.getDouble("accuracy");

           encodedString = user.getString("photo");

        } catch (JSONException e ) {
            Log.e("Depurando", "HomeFragment -> parseJson -> JSONException: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}