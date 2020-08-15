package com.srit.market.home.ui.more.change_password;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.srit.market.helpers.MyResponse;
import com.srit.market.register.login.LoginModel;

public class ChangePasswordViewModel extends ViewModel {


    private MutableLiveData<MyResponse> mutableLiveData;

    public void init(ChangePasswordModel changePasswordModel) {
        if (mutableLiveData != null) {
            return;
        }
        ChangePasswordRepository changePasswordRepository = ChangePasswordRepository.getInstance();
        mutableLiveData = changePasswordRepository.changePassword(changePasswordModel);
    }

    LiveData<MyResponse> getChangePasswordRepository() {
        return mutableLiveData;
    }
}