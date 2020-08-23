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
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;
import com.srit.market.R;
import com.srit.market.databinding.FragmentLoginBinding;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.register.RegisterFragment;


public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    LoginViewModel loginViewModel;
    String title,username,password,requestError;
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
        binding.loginBtn.setText(title);
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
                if(loginViewModel!=null){
                    loginViewModel.setMutableLiveDataNull();
                }
                setupViewModel();
            }
        });
        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefHelper.getInstance().setAccessToken(null);
                MainActivity.newInstance(getContext());
            }
        });
    }

    private Pair<String,Boolean> validation(){
        if(binding.username.getText().toString().length()==0){
            return new Pair<>(username,false);
        }
        if(binding.password.getText().toString().length() ==0){
            return new Pair<>(password,false);
        }

        return new Pair("",true);
    }

    private void setupViewModel(){
        Pair<String,Boolean> pair=validation();
        if(pair.second){
            showProgressBar();

            loginViewModel  = new ViewModelProvider(this, new ViewModelProvider.Factory() {
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
                                getActivity().finish();
                            } else {
                                // call failed.
                                String s = myResponse.getError();
                                Log.d("LoginError", s);
                                CustomSnackView.showSnackBar(binding.loginLayout,requestError,true);
                            }
                            hideProgressBar();
                        }
                    }
            );
        }else{
            CustomSnackView.showSnackBar(binding.loginLayout,pair.first,true);
        }
    }

    private void setLanguage(){
        //true -> arabic
        if(SharedPrefHelper.getInstance().getLanguage()){
            binding.loginLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.username.setHint(R.string.ar_username);
            binding.password.setHint(R.string.ar_password);
            binding.password.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            binding.skipBtn.setText(R.string.ar_skip);
            binding.loginBtn.setText(R.string.ar_login_title);
            binding.registerBtn.setText(R.string.ar_register_title);
            title = getString(R.string.ar_login_title);
            username = getString(R.string.ar_enter_username);
            password = getString(R.string.ar_enter_password);
            requestError=getString(R.string.ar_connection_error);
        }else{
            binding.loginLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            title = getString(R.string.en_login_title);
            binding.username.setHint(R.string.username);
            binding.password.setHint(R.string.password);
            binding.skipBtn.setText(R.string.skip);
            binding.loginBtn.setText(R.string.en_login_title);
            binding.registerBtn.setText(R.string.register_title);
            username = getString(R.string.en_enter_username);
            password = getString(R.string.en_enter_password);
            requestError=getString(R.string.en_connection_error);
        }
    }

    public void goToRegister(){
        RegisterFragment.newInstance(getParentFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setLanguage();
        return binding.getRoot();
    }
}