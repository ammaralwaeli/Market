package com.srit.market.home.ui.more.change_password;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.srit.market.R;
import com.srit.market.databinding.FragmentChangePasswordBinding;
import com.srit.market.databinding.FragmentLoginBinding;
import com.srit.market.db.OrderItemRepository;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.home.ui.new_order.ShopingCartActivity;
import com.srit.market.register.login.LoginFragment;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.login.LoginViewModel;
import com.srit.market.register.register.RegisterFragment;

public class ChangePasswordFragment extends Fragment {

    FragmentChangePasswordBinding binding;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    public static void newInstance(FragmentManager fr) {
        fr.beginTransaction().replace(R.id.nav_host_fragment, new ChangePasswordFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.changePasswordBtn.setText("");
    }

    private void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
        binding.changePasswordBtn.setText("Change Password");
    }


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


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        binding.changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupViewModel();
            }
        });
    }

    private Pair<String, Boolean> validation() {
        if (binding.oldPassword.getText().toString().length() == 0) {
            return new Pair<>("Please, Enter your old Password", false);
        }
        if (binding.newPassword.getText().toString().length() == 0) {
            return new Pair<>("Please, Enter your new password", false);
        }
        if (binding.confirmPassword.getText().toString().length() == 0) {
            return new Pair<>("Please, Enter confirm your password", false);
        }

        if (!binding.confirmPassword.getText().toString().equals(binding.newPassword.getText().toString())) {
            return new Pair<>("Password not matched!", false);
        }

        return new Pair("", true);
    }

    private void setupViewModel() {
        Pair<String, Boolean> pair = validation();
        if (pair.second) {
            showProgressBar();

            final ChangePasswordViewModel changePasswordViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
                @SuppressWarnings("unchecked")
                @Override
                public <T extends ViewModel> T create(final Class<T> modelClass) {
                    if (modelClass.equals(ChangePasswordViewModel.class)) {
                        return (T) new ChangePasswordViewModel();
                    } else {
                        return null;
                    }
                }
            }).get(ChangePasswordViewModel.class);
            String newPassword = binding.newPassword.getText().toString();
            String oldPassword = binding.oldPassword.getText().toString();
            ChangePasswordModel changePasswordModel = new ChangePasswordModel(oldPassword, newPassword);
            changePasswordViewModel.init(changePasswordModel);
            changePasswordViewModel.getChangePasswordRepository().observe(ChangePasswordFragment.this, new Observer<MyResponse>() {
                        @Override
                        public void onChanged(MyResponse myResponse) {

                            JsonObject jsonObject = (JsonObject) myResponse.getPosts();
                            if (myResponse == null) {
                                Log.d("LoginError", "null");
                                return;
                            }
                            if (myResponse.getError() == null) {
                                CustomSnackView.showSnackBar(binding.changePasswordLayout, jsonObject.get("message").getAsString(), false);
                            } else {
                                // call failed.
                                String s = myResponse.getError();
                                Log.d("LoginError", s);
                                CustomSnackView.showSnackBar(binding.changePasswordLayout, s, true);
                            }
                            hideProgressBar();
                        }
                    }
            );
        } else {
            CustomSnackView.showSnackBar(binding.changePasswordLayout, pair.first, true);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChangePasswordBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}