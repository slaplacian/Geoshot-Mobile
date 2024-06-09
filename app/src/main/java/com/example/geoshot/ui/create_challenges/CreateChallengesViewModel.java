package com.example.geoshot.ui.create_challenges;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateChallengesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreateChallengesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}