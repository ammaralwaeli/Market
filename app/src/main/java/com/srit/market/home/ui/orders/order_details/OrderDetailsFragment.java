package com.srit.market.home.ui.orders.order_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.srit.market.R;
import com.srit.market.databinding.FragmentOrderDetailsBinding;
import com.srit.market.helpers.MyResponse;

public class OrderDetailsFragment extends Fragment {

    FragmentOrderDetailsBinding binding;
    static int orderId;
    OrderDetailsAdapter adapter;
    public OrderDetailsFragment() {
        // Required empty public constructor
    }

    public static void newInstance(FragmentManager fr,int id) {
        fr.beginTransaction().replace(R.id.nav_host_fragment, new OrderDetailsFragment())
                .addToBackStack(null)
                .commit();
        orderId=id;
    }



    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recy.setVisibility(View.GONE);
        binding.totalLayout.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
        binding.recy.setVisibility(View.VISIBLE);
        binding.totalLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        setupViewModel();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel(){
            showProgressBar();

            final OrderDetailsViewModel orderDetailsViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
                @SuppressWarnings("unchecked")
                @Override
                public <T extends ViewModel> T create(final Class<T> modelClass) {
                    if (modelClass.equals(OrderDetailsViewModel.class)) {
                        return (T) new OrderDetailsViewModel();
                    } else {
                        return null;
                    }
                }
            }).get(OrderDetailsViewModel.class);
            orderDetailsViewModel.init(orderId);
            orderDetailsViewModel.getOrderDetailsRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                        @Override
                        public void onChanged(MyResponse myResponse) {

                            AllOrders allOrders=(AllOrders) myResponse.getPosts();
                            if (myResponse == null) {
                                Log.d("LoginError", "null");
                                return;
                            }
                            if (myResponse.getError() == null) {
                                adapter=new OrderDetailsAdapter(getContext(),allOrders.getResults());
                                binding.recy.setAdapter(adapter);
                                binding.totalPrice.setText(allOrders.getTotalPrice()+"");
                                int count=0;
                                for(OrderDetailsModel order:allOrders.getResults()){
                                    count+=order.getItemCounts();
                                }
                                binding.count.setText(count+"");
                            } else {
                                // call failed.
                                String s = myResponse.getError();
                                Log.d("LoginError", s);
                            }
                            hideProgressBar();
                        }
                    }
            );

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}