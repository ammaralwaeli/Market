package com.srit.market.home.ui.home.slider;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class SliderViewModel extends ViewModel {

    private MutableLiveData<MyResponse> mutableLiveDataSlider;

    public void initSlider() {
        if (mutableLiveDataSlider != null) {
            return;
        }
        SliderRepository sliderRepository = SliderRepository.getInstance();
        mutableLiveDataSlider = sliderRepository.getSlider();
    }
    public LiveData<MyResponse> getSliderRepository() {
        return mutableLiveDataSlider;
    }
}