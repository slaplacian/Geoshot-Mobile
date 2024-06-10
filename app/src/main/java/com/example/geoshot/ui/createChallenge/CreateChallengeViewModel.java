package com.example.geoshot.ui.createChallenge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateChallengeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreateChallengeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}