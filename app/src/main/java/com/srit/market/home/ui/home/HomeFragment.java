package com.srit.market.home.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.srit.market.databinding.FragmentHomeBinding;
import com.srit.market.helpers.MyResponse;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.category.CategoryAdapter;
import com.srit.market.home.ui.home.category.CategoryModel;
import com.srit.market.home.ui.home.item.ItemsFragment;
import com.srit.market.home.ui.home.slider.SliModel;
import com.srit.market.home.ui.home.slider.SliderAdapterExample;
import com.srit.market.home.ui.home.slider.SliderViewModel;

import java.util.Objects;

public class HomeFragment extends Fragment implements CategoryAdapter.ItemListener {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private SliderAdapterExample sliderAdapter;
    CategoryAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.recy.setLayoutManager(new GridLayoutManager(getContext(), 2));
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle("Home");
        setupViewModel();
        setupSliderViewModel();
        return binding.getRoot();
    }

    private void setupSliderViewModel(){
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
                            binding.imageSlider.setSliderAdapter(sliderAdapter);
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                        }
                    }
                }
        );
    }


    private void setupViewModel(){
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
                        }
                    }
                }
        );
    }

    @Override
    public void onItemClick(CategoryModel categoryModel) {
        ItemsFragment.newInstance(getParentFragmentManager(),categoryModel);
    }
}