package com.example.geoshot.ui.create_challenges;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.geoshot.databinding.FragmentCretateChallengeBinding;

public class CreateChallengesFragment extends Fragment {

    private FragmentCretateChallengeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateChallengesViewModel createChallengesViewModel =
                new ViewModelProvider(this).get(CreateChallengesViewModel.class);

        binding = FragmentCretateChallengeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.createChallenges;
        createChallengesViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}