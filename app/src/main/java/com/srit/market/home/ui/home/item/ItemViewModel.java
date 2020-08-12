package com.srit.market.home.ui.home.item;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class ItemViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(int id) {
        if (mutableLiveData != null) {
            return;
        }
        ItemRepository itemRepository = ItemRepository.getInstance();
        mutableLiveData = itemRepository.getItems(id);
    }

    LiveData<MyResponse> getItemRepository() {
        return mutableLiveData;
    }
}