package com.srit.market.register.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;
import com.srit.market.register.login.LoginModel;

public class RegisterViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(RegisterModel registerModel) {
        if (mutableLiveData != null) {
            return;
        }
        RegisterRepository registerRepository = RegisterRepository.getInstance();
        mutableLiveData = registerRepository.register(registerModel);
    }
    public void setMutableLiveDataNull(){
        this.mutableLiveData=null;
    }
    LiveData<MyResponse> getRegisterRepository() {
        return mutableLiveData;
    }
}