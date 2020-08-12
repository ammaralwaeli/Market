package com.srit.market.home.ui.more;

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

import com.srit.market.databinding.FragmentMoreBinding;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.RegisterActivity;

import java.util.Objects;


public class MoreFragment extends Fragment {

    FragmentMoreBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle("More");
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefHelper.getInstance().setAccessToken(null);
                RegisterActivity.newInstance(getContext());
            }
        });
        setupViewModel();
        return binding.getRoot();
    }



    private void setupViewModel(){
        final ProfileViewModel profileViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
            @SuppressWarnings("unchecked")
            @Override
            public <T extends ViewModel> T create(final Class<T> modelClass) {
                if (modelClass.equals(ProfileViewModel.class)) {
                    return (T) new ProfileViewModel();
                } else {
                    return null;
                }
            }
        }).get(ProfileViewModel.class);
        profileViewModel.init();
        profileViewModel.getProfileRepository().observe(getViewLifecycleOwner(), new Observer<MyResponse>() {
                    @Override
                    public void onChanged(MyResponse myResponse) {

                        Profile profile=(Profile) myResponse.getPosts();
                        if (myResponse == null) {
                            Log.d("LoginError", "null");
                            return;
                        }
                        if (myResponse.getError() == null) {
                            binding.setItem(profile.getProfile());
                        } else {
                            // call failed.
                            String s = myResponse.getError();
                            Log.d("LoginError", s);
                        }
                    }
                }
        );
    }
}