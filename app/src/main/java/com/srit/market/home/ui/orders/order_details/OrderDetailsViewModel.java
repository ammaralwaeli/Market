package com.srit.market.home.ui.orders.order_details;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;
import com.srit.market.register.login.LoginModel;

public class OrderDetailsViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(int orderId) {
        if (mutableLiveData != null) {
            return;
        }
        OrderDetailsRepository orderDetailsRepository = OrderDetailsRepository.getInstance();
        mutableLiveData = orderDetailsRepository.getOrderDetails(orderId);
    }

    public LiveData<MyResponse> getOrderDetailsRepository() {
        return mutableLiveData;
    }
}