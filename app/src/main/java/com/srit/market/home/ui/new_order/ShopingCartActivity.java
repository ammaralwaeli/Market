package com.srit.market.home.ui.new_order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.srit.market.R;
import com.srit.market.databinding.ActivityShopingCartBinding;
import com.srit.market.register.RegisterActivity;

public class ShopingCartActivity extends AppCompatActivity {

    ActivityShopingCartBinding binding;

    public static void newInstance(Context context) {

        Intent in = new Intent(context, ShopingCartActivity.class);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_shoping_cart);
        NewOrderFragment.newInstance(getSupportFragmentManager());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setCustomTitle(String title){
        binding.setTitle(title);
    }
}