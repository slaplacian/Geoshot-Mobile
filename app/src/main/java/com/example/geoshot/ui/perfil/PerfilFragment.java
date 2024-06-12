package com.example.geoshot.ui.perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.geoshot.BaseActivity;
import com.example.geoshot.R;

public class PerfilFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
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
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
    }

}