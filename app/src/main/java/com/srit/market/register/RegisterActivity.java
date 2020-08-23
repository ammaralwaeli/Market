package com.srit.market.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

    private AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.ar_lang_message)
                .setPositiveButton(R.string.arabic, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefHelper.getInstance().setLanguage(true);
                    }
                })
                .setNegativeButton(R.string.english, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefHelper.getInstance().setLanguage(false);
                    }
                });

        return builder.create();
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