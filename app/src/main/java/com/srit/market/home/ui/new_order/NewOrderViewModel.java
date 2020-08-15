package com.srit.market.home.ui.new_order;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class NewOrderViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(OrderPostModel orderPostModel) {
        if (mutableLiveData != null) {
            return;
        }
        NewOrderRepository newOrderRepository = NewOrderRepository.getInstance();
        mutableLiveData = newOrderRepository.addOrder(orderPostModel);
    }

    LiveData<MyResponse> getNewOrderRepository() {
        return mutableLiveData;
    }
}