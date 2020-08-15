package com.srit.market.home.ui.home.slider;

import android.os.Bundle;
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

import com.srit.market.R;
import com.srit.market.databinding.FragmentImageViewerBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.home.ui.new_order.ShopingCartActivity;

public class ImageViewerFragment extends Fragment {



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

    private static String s;
    public static void newInstance(FragmentManager fr, String src) {
        fr.beginTransaction().replace(R.id.nav_host_fragment, new ImageViewerFragment())
                .addToBackStack(null)
                .commit();
        s=src;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FragmentImageViewerBinding binding = FragmentImageViewerBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        binding.setSrc(s);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }
}
