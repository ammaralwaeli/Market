package com.srit.market.home.ui.new_order;

import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.srit.market.R;
import com.srit.market.databinding.FragmentNewOrderBinding;
import com.srit.market.db.OrderItem;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewOrderFragment extends Fragment {

    FragmentNewOrderBinding binding;
    List<OrderItem> items;
    ArrayList<Integer> itemId,itemCount;

    String title,buy,sent,requestError;

    public NewOrderFragment() {
        // Required empty public constructor
    }

    private void setLanguage(){
        if(SharedPrefHelper.getInstance().getLanguage()){
            binding.newOrderLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.buyBtn.setText(R.string.ar_buy);
            binding.countHeader.setText(R.string.ar_total_count);
            binding.totalHeader.setText(getString(R.string.ar_total_price));
            title = getString(R.string.ae_cart);
            sent=getString(R.string.ar_sent_success);
            requestError=getString(R.string.ar_connection_error);
        } else{
            binding.newOrderLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            binding.buyBtn.setText(R.string.en_buy);
            binding.countHeader.setText(R.string.en_total_count);
            binding.totalHeader.setText(R.string.en_total_price);
            title = getString(R.string.en_cart);
            sent=getString(R.string.en_sent_success);
            requestError=getString(R.string.en_connection_error);
        }
    }

    public static void newInstance(FragmentManager fr) {
        fr.beginTransaction().replace(R.id.fragmentContainerShoppingCart, new NewOrderFragment())
                .addToBackStack(null)
                .commit();
    }

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
        binding.buyBtn.setText(buy);
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
                            CustomSnackView.showSnackBar(binding.newOrderLayout,sent,false);
                            for (OrderItem item:items){
                                orderItemRepository.deleteItem(item);
                                getActivity().onBackPressed();
                            }
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                            CustomSnackView.showSnackBar(binding.newOrderLayout,requestError,true);
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
        setLanguage();
        ((ShopingCartActivity) Objects.requireNonNull(getActivity())).setCustomTitle(title);
        binding.buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupViewModel();
            }
        });
        return binding.getRoot();
    }
}