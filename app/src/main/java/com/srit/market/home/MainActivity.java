package com.srit.market.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        BottomNavigationView navView = binding.navView;
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        setSupportActionBar(binding.toolbar);


    }

    public void setCustomTitle(String title) {
        binding.setTitle(title);
    }
}