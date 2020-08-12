package com.srit.market.home.ui.more;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class ProfileViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        ProfileRepository profileRepository = ProfileRepository.getInstance();
        mutableLiveData = profileRepository.getProfile();
    }

    LiveData<MyResponse> getProfileRepository() {
        return mutableLiveData;
    }
}