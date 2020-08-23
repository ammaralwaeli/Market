package com.srit.market.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.srit.market.R;
import com.srit.market.databinding.ActivityMainBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.ui.new_order.NewOrderAdapter;
import com.srit.market.home.ui.new_order.NewOrderFragment;
import com.srit.market.home.ui.new_order.ShopingCartActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;

    public static void newInstance(Context context) {

        Intent in = new Intent(context, MainActivity.class);
        context.startActivity(in);
    }

    BottomNavigationView navView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        setSupportActionBar(binding.toolbar);
        setLanguage();

    }

    public void setLanguage(){
        if(SharedPrefHelper.getInstance().getLanguage()){
            binding.container.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            navView.getMenu().getItem(0).setTitle(getString(R.string.ar_home_title));
            navView.getMenu().getItem(1).setTitle(getString(R.string.ar_orders));
            navView.getMenu().getItem(2).setTitle(getString(R.string.ar_more_title));
        }else{
            binding.container.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            navView.getMenu().getItem(0).setTitle(getString(R.string.title_home));
            navView.getMenu().getItem(1).setTitle(getString(R.string.orders));
            navView.getMenu().getItem(2).setTitle(getString(R.string.en_more_title));
        }
    }

    public void setCustomTitle(String title) {
        binding.setTitle(title);
    }
}