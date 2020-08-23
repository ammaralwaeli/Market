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
import com.srit.market.home.ui.more.MoreFragment;
import com.srit.market.home.ui.new_order.ShopingCartActivity;
import com.srit.market.register.login.LoginFragment;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.login.LoginViewModel;
import com.srit.market.register.register.RegisterFragment;

public class ChangePasswordFragment extends Fragment {

    FragmentChangePasswordBinding binding;

    String title, enter, please, newPassword, oldPassword, confirmPassword, not_matched, requestError,requestSuccess;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    private void setLanguage() {
        if (SharedPrefHelper.getInstance().getLanguage()) {

            title = getString(R.string.ar_change_password);
            enter = getString(R.string.ar_enter);
            please = getString(R.string.ar_please);
            newPassword = getString(R.string.ar_new_password);
            oldPassword = getString(R.string.ar_old_password);
            confirmPassword = getString(R.string.ar_confirm_password);
            not_matched = getString(R.string.ar_pass_not_mathced);
            requestError = getString(R.string.ar_connection_error);
            requestSuccess = getString(R.string.ar_pass_changed_success);
            binding.confirmPassword.setHint(confirmPassword);
            binding.newPassword.setHint(newPassword);
            binding.oldPassword.setHint(oldPassword);
            binding.changePasswordBtn.setText(title);
            binding.changePasswordLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        } else {

            title = getString(R.string.en_change_password);
            enter = getString(R.string.en_enter);
            please = getString(R.string.en_please);
            newPassword = getString(R.string.en_new_password);
            oldPassword = getString(R.string.en_old_password);
            confirmPassword = getString(R.string.en_confirm_password);
            not_matched = getString(R.string.en_pass_not_mathced);
            requestError = getString(R.string.en_connection_error);
            requestSuccess = getString(R.string.en_pass_changed_success);
            binding.confirmPassword.setHint(confirmPassword);
            binding.newPassword.setHint(newPassword);
            binding.oldPassword.setHint(oldPassword);
            binding.changePasswordBtn.setText(title);
            binding.changePasswordLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
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
        binding.changePasswordBtn.setText(title);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.back, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {

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
            return new Pair<>(please.concat(enter.concat(oldPassword)), false);
        }
        if (binding.newPassword.getText().toString().length() == 0) {
            return new Pair<>(please.concat(enter.concat(newPassword)), false);
        }
        if (binding.confirmPassword.getText().toString().length() == 0) {
            return new Pair<>(please.concat(confirmPassword), false);
        }

        if (!binding.confirmPassword.getText().toString().equals(binding.newPassword.getText().toString())) {
            return new Pair<>(not_matched, false);
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
                                CustomSnackView.showSnackBar(binding.changePasswordLayout, requestSuccess, false);
                            } else {
                                // call failed.
                                String s = myResponse.getError();
                                Log.d("LoginError", s);
                                CustomSnackView.showSnackBar(binding.changePasswordLayout, requestError, true);
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
        setLanguage();
        return binding.getRoot();
    }
}