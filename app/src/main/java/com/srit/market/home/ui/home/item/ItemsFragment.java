package com.srit.market.home.ui.home.item;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.srit.market.databinding.FragmentItemBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.category.CategoryModel;
import com.srit.market.home.ui.item_details.ItemDetailsFragment;
import com.srit.market.home.ui.new_order.ShopingCartActivity;

import java.util.Objects;


public class ItemsFragment extends Fragment implements ItemAdapter.ItemListener {

    FragmentItemBinding binding;
    ItemAdapter adapter;
    static CategoryModel category;


    public ItemsFragment() {
        // Required empty public constructor
    }

    public static void newInstance(FragmentManager fr, CategoryModel cat) {
        fr.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment())
                .addToBackStack(null)
                .commit();
        category = cat;
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.recy.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
        binding.recy.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViewModel();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(SharedPrefHelper.getInstance().getAccessToken()==null){
            inflater.inflate(R.menu.back, menu);
        }else {
            inflater.inflate(R.menu.shopping_cart_back, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            getActivity().onBackPressed();
        } else if (item.getItemId() == R.id.shopping) {
            if(!(new OrderItemRepository(getContext()).getItems().size()==0)){
                ShopingCartActivity.newInstance(getContext());
            }

        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewModel() {

        showProgressBar();

        final ItemViewModel itemViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(ItemViewModel.class)) {
                    return (T) new ItemViewModel();
                } else {
                    return null;
                }
            }
        }).get(ItemViewModel.class);
        itemViewModel.init(category.getCategoryID());
        itemViewModel.getItemRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        Items items = (Items) myResponse.getPosts();
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            adapter = new ItemAdapter(getContext(), items.getItems());
                            adapter.setListener(ItemsFragment.this);
                            binding.recy.setAdapter(adapter);
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            CustomSnackView.showSnackBar(binding.itemLayout, s, true);
                        }
                        hideProgressBar();
                    }
                }
        );
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentItemBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(category.getName());
        return binding.getRoot();
    }

    @Override
    public void onItemClick(ItemModel itemModel) {
        if (SharedPrefHelper.getInstance().getAccessToken() != null) {
            ItemDetailsFragment.newInstance(getParentFragmentManager(), itemModel);
        }
    }
}