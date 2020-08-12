package com.srit.market.register.register;

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
import com.srit.market.databinding.FragmentRegisterBinding;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.login.LoginFragment;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.login.LoginViewModel;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    public RegisterFragment() {
        // Required empty public constructor
    }
    public static void newInstance(FragmentManager fr) {
        fr.beginTransaction().replace(R.id.fragmentContainer, new RegisterFragment())
                .addToBackStack(null)
                .commit();
    }


    private void showProgressBar(){
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.registerBtn.setText("");
    }

    private void hideProgressBar(){
        binding.progressBar.setVisibility(View.GONE);
        binding.registerBtn.setText(getString(R.string.register_title));
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        binding.backToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupViewModel();
            }
        });
    }


    private void setupViewModel(){
        if(true/*Validation*/){
            showProgressBar();
            final RegisterViewModel registerViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
                @SuppressWarnings("unchecked")
                @Override
                public <T extends ViewModel> T create(final Class<T> modelClass) {
                    if (modelClass.equals(RegisterViewModel.class)) {
                        return (T) new RegisterViewModel();
                    } else {
                        return null;
                    }
                }
            }).get(RegisterViewModel.class);
            RegisterModel registerModel=new RegisterModel(binding.username.getText().toString(),
                    binding.password.getText().toString(),
                    binding.address.getText().toString(),
                    binding.gender.getText().toString(),
                    Integer.parseInt(binding.phone.getText().toString()));
            registerViewModel.init(registerModel);
            registerViewModel.getRegisterRepository().observe(RegisterFragment.this, new Observer<MyResponse>() {
                        @Override
                        public void onChanged(MyResponse myResponse) {
                            JsonObject jsonObject=(JsonObject) myResponse.getPosts();
                            if (myResponse == null) {
                                Log.d("registerError", "null");
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
                                Log.d("registerError", s);
                            }
                            hideProgressBar();
                        }
                    }
            );
        }
    }

    public void goToLogin(){
        LoginFragment.newInstance(getFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        return binding.getRoot();
    }
}