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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(getString(R.string.orders));
        setupViewModel();
        return binding.getRoot();
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
            CustomSnackView.showSnackBar(binding.orderLayout, "Must Login", true);
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
                            CustomSnackView.showSnackBar(binding.orderLayout,s,true);
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