package com.srit.market.home.ui.orders;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;
import com.srit.market.home.ui.home.HomeRepository;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<MyResponse> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
        OrderRepository orderRepository = OrderRepository.getInstance();
        mutableLiveData = orderRepository.getOrders();
    }

    LiveData<MyResponse> getOrderRepository() {
        return mutableLiveData;
    }
}