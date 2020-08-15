package com.srit.market.home.ui.new_order;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.FtsOptions;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.srit.market.R;
import com.srit.market.databinding.FragmentLoginBinding;
import com.srit.market.databinding.FragmentNewOrderBinding;
import com.srit.market.db.OrderItem;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.HomeFragment;
import com.srit.market.home.ui.home.HomeViewModel;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.category.CategoryAdapter;
import com.srit.market.register.login.LoginFragment;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.login.LoginViewModel;
import com.srit.market.register.register.RegisterFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewOrderFragment extends Fragment {

    FragmentNewOrderBinding binding;
    List<OrderItem> items;
    ArrayList<Integer> itemId,itemCount;
    public NewOrderFragment() {
        // Required empty public constructor
    }

    public static void newInstance(FragmentManager fr) {
        fr.beginTransaction().replace(R.id.fragmentContainerShoppingCart, new NewOrderFragment())
                .addToBackStack(null)
                .commit();
    }
/*
    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setText("");
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setText(getString(R.string.login_title));
    }
*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        items=getItems();
        NewOrderAdapter adapter=new NewOrderAdapter(getContext(),items);
        binding.recy.setAdapter(adapter);
        binding.totalPrice.setText(getTotalPrice());
        binding.count.setText(getCount());
    }


    private String getTotalPrice(){
        int total=0;

        for(OrderItem item:items){

            total+=item.totalPrice;
        }
        return total+"";
    }

    private OrderPostModel getOrder(){
        itemId=new ArrayList<>();
        itemCount=new ArrayList<>();
        for(OrderItem item:items){
            itemId.add(item.item_id);
            itemCount.add(item.itemCount);
        }
        return new OrderPostModel(itemId,itemCount,Integer.parseInt(getTotalPrice()));
    }
    private String getCount(){
        int count=0;
        for(OrderItem item:items){
            count+=item.itemCount;
        }
        return count+"";
    }
    private List<OrderItem> getItems(){
        OrderItemRepository order =new OrderItemRepository(getContext());
        return order.getItems();
    }

    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.buyBtn.setText("");
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
        binding.buyBtn.setText("Buy");
    }

    private void setupViewModel(){
        showProgressBar();
        final NewOrderViewModel newOrderViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(NewOrderViewModel.class)) {
                    return (T) new NewOrderViewModel();
                } else {
                    return null;
                }
            }
        }).get(NewOrderViewModel.class);
        newOrderViewModel.init(getOrder());
        newOrderViewModel.getNewOrderRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            OrderItemRepository orderItemRepository=new OrderItemRepository(getContext());
                            List<OrderItem> items=orderItemRepository.getItems();
                            CustomSnackView.showSnackBar(binding.newOrderLayout,"Order Sent Successfully",false);
                            for (OrderItem item:items){
                                orderItemRepository.deleteItem(item);
                                getActivity().onBackPressed();
                            }
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                            CustomSnackView.showSnackBar(binding.newOrderLayout,s,true);
                        }
                        hideProgressBar();
                    }
                }
        );
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewOrderBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        ((ShopingCartActivity) Objects.requireNonNull(getActivity())).setCustomTitle("Cart");
        binding.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupViewModel();
            }
        });
        return binding.getRoot();
    }
}