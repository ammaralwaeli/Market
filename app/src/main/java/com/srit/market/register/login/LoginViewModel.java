package com.srit.market.register.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;

public class LoginViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(LoginModel loginModel) {
        if (mutableLiveData != null) {
            return;
        }
        LoginRepository loginRepository = LoginRepository.getInstance();
        mutableLiveData = loginRepository.login(loginModel);
    }

    LiveData<MyResponse> getLoginRepository() {
        return mutableLiveData;
    }
}