package com.srit.market.home.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class HomeViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        HomeRepository homeRepository = HomeRepository.getInstance();
        mutableLiveData = homeRepository.getCategory();
    }

    LiveData<MyResponse> getHomeRepository() {
        return mutableLiveData;
    }
}