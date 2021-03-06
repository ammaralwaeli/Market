package com.srit.market.home.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.srit.market.R;
import com.srit.market.databinding.FragmentHomeBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.category.CategoryAdapter;
import com.srit.market.home.ui.home.category.CategoryModel;
import com.srit.market.home.ui.home.item.ItemsFragment;
import com.srit.market.home.ui.home.slider.ImageViewerFragment;
import com.srit.market.home.ui.home.slider.SliModel;
import com.srit.market.home.ui.home.slider.SliderAdapterExample;
import com.srit.market.home.ui.home.slider.SliderViewModel;
import com.srit.market.home.ui.new_order.ShopingCartActivity;

import java.util.Objects;

public class HomeFragment extends Fragment implements CategoryAdapter.ItemListener,SliderAdapterExample.ItemListener {


    private FragmentHomeBinding binding;
    private SliderAdapterExample sliderAdapter;
    CategoryAdapter adapter;
    String title;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.recy.setLayoutManager(new GridLayoutManager(getContext(), 2));
        if(SharedPrefHelper.getInstance().getLanguage()){
            title=getString(R.string.ar_home_title);
        }else{
            title=getString(R.string.en_home_titile);
        }
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(title);
        if(SharedPrefHelper.getInstance().getAccessToken()!=null) {
            setHasOptionsMenu(true);
        }
        setupViewModel();
        setupSliderViewModel();
        return binding.getRoot();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shopping_cart, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.shopping) {
            if(new OrderItemRepository(getContext()).getItems().size()!=0) {
                ShopingCartActivity.newInstance(getContext());
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private void showProgressBarForSlider(){
        binding.progressBarContainer.setVisibility(View.VISIBLE);
        binding.cvSliderMainActivity.setVisibility(View.GONE);
    }

    private void hideProgressBarForSlider(){
        binding.progressBarContainer.setVisibility(View.GONE);
        binding.cvSliderMainActivity.setVisibility(View.VISIBLE);
    }

    private void showProgressBarForRecycler(){
        binding.progressBarContainer.setVisibility(View.VISIBLE);
        binding.recy.setVisibility(View.GONE);
    }

    private void hideProgressBarForRecycler(){
        binding.progressBarContainer.setVisibility(View.GONE);
        binding.recy.setVisibility(View.VISIBLE);
    }


    private void setupSliderViewModel(){
        showProgressBarForSlider();
        final SliderViewModel sliderViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(SliderViewModel.class)) {
                    return (T) new SliderViewModel();
                } else {
                    return null;
                }
            }
        }).get(SliderViewModel.class);
        sliderViewModel.initSlider();
        sliderViewModel.getSliderRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        SliModel sliders=(SliModel) myResponse.getPosts();
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            sliderAdapter=new SliderAdapterExample(getContext());
                            sliderAdapter.renewItems(sliders.getResults());
                            sliderAdapter.setListener(HomeFragment.this);
                            binding.imageSlider.setSliderAdapter(sliderAdapter);
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                            CustomSnackView.showSnackBar(binding.homeLayout,s,true);
                        }
                        hideProgressBarForSlider();
                    }
                }
        );
    }


    private void setupViewModel(){
        showProgressBarForRecycler();
        final HomeViewModel homeViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(HomeViewModel.class)) {
                    return (T) new HomeViewModel();
                } else {
                    return null;
                }
            }
        }).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getHomeRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        CatModel categories=(CatModel) myResponse.getPosts();
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            adapter=new CategoryAdapter(getContext(),categories.getCategory());
                            adapter.setListener(HomeFragment.this);
                            binding.recy.setAdapter(adapter);
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                            CustomSnackView.showSnackBar(binding.homeLayout,s,true);
                        }
                        hideProgressBarForRecycler();
                    }
                }
        );
    }

    @Override
    public void onItemClick(CategoryModel categoryModel) {
        ItemsFragment.newInstance(getParentFragmentManager(),categoryModel);
    }

    @Override
    public void onImageClick(String src) {
        ImageViewerFragment.newInstance(getParentFragmentManager(),src);
    }
}