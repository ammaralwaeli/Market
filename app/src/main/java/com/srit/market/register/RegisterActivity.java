package com.srit.market.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.srit.market.R;
import com.srit.market.databinding.ActivityRegisterBinding;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.login.LoginFragment;

public class RegisterActivity extends AppCompatActivity {



    public static void newInstance(Context context) {

        Intent in = new Intent(context, RegisterActivity.class);
        context.startActivity(in);
    }

    ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_register);
        SharedPrefHelper.init(this);
        if(SharedPrefHelper.getInstance().getAccessToken() !=null){
            MainActivity.newInstance(this);
            finish();
        }
        LoginFragment.newInstance(getSupportFragmentManager());
    }
}