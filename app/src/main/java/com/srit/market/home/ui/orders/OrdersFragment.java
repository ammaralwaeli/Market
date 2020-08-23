package com.srit.market.home.ui.orders;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.srit.market.R;
import com.srit.market.databinding.FragmentHomeBinding;
import com.srit.market.databinding.FragmentOrdersBinding;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.HomeViewModel;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.category.CategoryAdapter;
import com.srit.market.home.ui.orders.order_details.OrderDetailsFragment;

import java.util.Objects;

public class OrdersFragment extends Fragment implements OrderAdapter.ItemListener{

    private OrdersViewModel ordersViewModel;
    private FragmentOrdersBinding binding;
    OrderAdapter adapter;
    String title,notAuth,requestError;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setLanguage();
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(title);
        setupViewModel();
        return binding.getRoot();
    }

    private void setLanguage(){
        if(SharedPrefHelper.getInstance().getLanguage()){
            binding.orderLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            title=getString(R.string.ar_orders);
            notAuth=getString(R.string.ar_not_auth);
            requestError=getString(R.string.ar_connection_error);
        }else{
            binding.orderLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            title=getString(R.string.orders);
            notAuth=getString(R.string.en_not_auth);
            requestError=getString(R.string.en_connection_error);
        }
    }

    private void showProgressBar(){
        binding.progressBarContainer.setVisibility(View.VISIBLE);
        binding.recy.setVisibility(View.GONE);
    }

    private void hideProgressBar(){
        binding.progressBarContainer.setVisibility(View.GONE);
        binding.recy.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (SharedPrefHelper.getInstance().getAccessToken() == null) {
            binding.recy.setVisibility(View.GONE);
            CustomSnackView.showSnackBar(binding.orderLayout, notAuth, true);
            getActivity().onBackPressed();
        }
    }


    private void setupViewModel(){
        showProgressBar();
        final OrdersViewModel ordersViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(OrdersViewModel.class)) {
                    return (T) new OrdersViewModel();
                } else {
                    return null;
                }
            }
        }).get(OrdersViewModel.class);
        ordersViewModel.init();
        ordersViewModel.getOrderRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        OrdersModel orders=(OrdersModel) myResponse.getPosts();
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            adapter=new OrderAdapter(getContext(),orders.getOrders());
                            adapter.setListener(OrdersFragment.this);
                            binding.recy.setAdapter(adapter);
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                            CustomSnackView.showSnackBar(binding.orderLayout,requestError,true);
                        }
                        hideProgressBar();
                    }
                }
        );
    }

    @Override
    public void onItemClick(int orderId) {
        OrderDetailsFragment.newInstance(getParentFragmentManager(),orderId);
    }
}