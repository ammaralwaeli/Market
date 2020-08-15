package com.srit.market.home.ui.item_details;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.srit.market.R;
import com.srit.market.databinding.FragmentItemDetailsBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.item.ItemModel;
import com.srit.market.home.ui.new_order.ShopingCartActivity;

import java.util.Objects;

public class ItemDetailsFragment extends Fragment {


    static ItemModel item;
    FragmentItemDetailsBinding binding;

    public static void newInstance(FragmentManager fr, ItemModel itemModel) {
        fr.beginTransaction().replace(R.id.nav_host_fragment, new ItemDetailsFragment())
                .addToBackStack(null)
                .commit();
        item = itemModel;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shopping_cart_back, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            getActivity().onBackPressed();
        } else if (item.getItemId() == R.id.shopping) {
            if (!(new OrderItemRepository(getContext()).getItems().size() == 0)) {
                ShopingCartActivity.newInstance(getContext());
            }

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.setItem(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentItemDetailsBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(item.getItemName());
        setHasOptionsMenu(true);
        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrderBottomSheet.newInstance(item).show(getParentFragmentManager(), "");
            }
        });
        return binding.getRoot();
    }


}