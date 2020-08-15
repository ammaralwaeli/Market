package com.srit.market.home.ui.more;

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
import com.srit.market.databinding.FragmentMoreBinding;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.more.change_password.ChangePasswordFragment;
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
        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment.newInstance(getParentFragmentManager());
            }
        });
        setupViewModel();

        return binding.getRoot();
    }


    private void showProgressBar(){
        binding.progressBarContainer.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar(){
        binding.progressBarContainer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (SharedPrefHelper.getInstance().getAccessToken() == null) {
            hideComponents();
            CustomSnackView.showSnackBar(binding.moreLayout, "Must Login", true);
            getActivity().onBackPressed();
        }
    }

    private void hideComponents() {
        binding.changePassword.setVisibility(View.GONE);
        binding.location.setVisibility(View.GONE);
        binding.username.setVisibility(View.GONE);
        binding.rule.setVisibility(View.GONE);
        binding.signOut.setVisibility(View.GONE);
    }

    private void setupViewModel() {
        showProgressBar();
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

                        Profile profile = (Profile) myResponse.getPosts();
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
                            CustomSnackView.showSnackBar(binding.moreLayout, s, true);
                        }
                        hideProgressBar();
                    }
                }
        );
    }
}