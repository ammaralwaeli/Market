package com.srit.market.register.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.srit.market.R;
import com.srit.market.databinding.FragmentLoginBinding;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.register.RegisterFragment;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    public LoginFragment() {
        // Required empty public constructor
    }

    public static void newInstance(FragmentManager fr) {
        fr.beginTransaction().replace(R.id.fragmentContainer, new LoginFragment())
                .addToBackStack(null)
                .commit();
    }

    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.loginBtn.setText("");
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
        binding.loginBtn.setText(getString(R.string.login_title));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupViewModel();
            }
        });
    }

    private void setupViewModel(){
        if(true/*Validation*/){
            showProgressBar();

            final LoginViewModel loginViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
                @SuppressWarnings("unchecked")
                @Override
                public <T extends ViewModel> T create(final Class<T> modelClass) {
                    if (modelClass.equals(LoginViewModel.class)) {
                        return (T) new LoginViewModel();
                    } else {
                        return null;
                    }
                }
            }).get(LoginViewModel.class);
            LoginModel loginModel=new LoginModel(binding.username.getText().toString(),binding.password.getText().toString());
            loginViewModel.init(loginModel);
            loginViewModel.getLoginRepository().observe(LoginFragment.this, new Observer<MyResponse>() {
                        @Override
                        public void onChanged(MyResponse myResponse) {

                            JsonObject jsonObject=(JsonObject) myResponse.getPosts();
                            if (myResponse == null) {
                                Log.d("LoginError", "null");
                                return;
                            }
                            if (myResponse.getError() == null) {
                                String token =  jsonObject.get("token").getAsString();
                                SharedPrefHelper.getInstance().setAccessToken(token);
                                MainActivity.newInstance(getContext());
                                Log.d("token", token);
                            } else {
                                // call failed.
                                String s = myResponse.getError();
                                Log.d("LoginError", s);
                            }
                            hideProgressBar();
                        }
                    }
            );
        }
    }

    public void goToRegister(){
        RegisterFragment.newInstance(getFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}