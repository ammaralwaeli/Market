package com.srit.market.register.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.srit.market.databinding.FragmentRegisterBinding;
import com.srit.market.helpers.CustomSnackView;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.SharedPrefHelper;
import com.srit.market.home.MainActivity;
import com.srit.market.register.login.LoginFragment;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.login.LoginViewModel;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    RegisterViewModel registerViewModel;

    String title,username8,username0, password6,password0,selectGender,enterAddress,phone0,wrongPhone,male,female,requestError;

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
        binding.registerBtn.setText(title);
    }

    private void setLanguage(){
        if (SharedPrefHelper.getInstance().getLanguage()){
            binding.registerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.phone.setHint(R.string.ar_phone);
            binding.phone.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            binding.address.setHint(R.string.ar_address);
            binding.gender.setText(R.string.ar_gender);
            binding.password.setHint(R.string.ar_password);
            binding.username.setHint(R.string.ar_username);
            binding.password.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            binding.registerBtn.setText(R.string.ar_register_title);
            binding.backToLoginBtn.setText(R.string.ar_back_to_login);
            title=getString(R.string.ar_register_title);
            username0 = getString(R.string.ar_enter_username);
            username8 = getString(R.string.ar_little_username);
            password0 = getString(R.string.ar_enter_password);
            password6 = getString(R.string.ar_little_password);
            selectGender=getString(R.string.ar_select_gender);
            enterAddress=getString(R.string.ar_enter_address);
            phone0=getString(R.string.ar_enter_phone);
            wrongPhone=getString(R.string.ar_wrong_phone);
            male=getString(R.string.ar_male);
            female=getString(R.string.ar_female);
            requestError=getString(R.string.ar_connection_error);
        }else{

            binding.registerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            binding.phone.setHint(R.string.en_phone);
            binding.address.setHint(R.string.en_address);
            binding.gender.setText(R.string.en_gender);
            binding.password.setHint(R.string.password);
            binding.username.setHint(R.string.username);
            binding.registerBtn.setText(R.string.register_title);
            binding.backToLoginBtn.setText(R.string.back_to_login);
            title=getString(R.string.register_title);
            username0 = getString(R.string.en_enter_username);
            username8 = getString(R.string.en_little_username);
            password0 = getString(R.string.en_enter_password);
            password6 = getString(R.string.en_little_password);
            selectGender=getString(R.string.en_select_gender);
            enterAddress=getString(R.string.en_enter_address);
            phone0=getString(R.string.en_enter_phone);
            wrongPhone=getString(R.string.en_wrong_phone);
            male=getString(R.string.en_male);
            female=getString(R.string.en_female);
            requestError=getString(R.string.en_connection_error);
        }
    }


    private Pair<String,Boolean> validation(){
        if(binding.username.getText().toString().length()<8){
            return new Pair<>(username8,false);
        }

        if(binding.username.getText().toString().length()==0){
            return new Pair<>(username0,false);
        }
        if(binding.password.getText().toString().length() <6){
            return new Pair<>(password6,false);
        }

        if(binding.password.getText().toString().length() ==0){
            return new Pair<>(password0,false);
        }

        if(binding.gender.getText().toString().length() ==0){
            return new Pair<>(selectGender,false);
        }

        if(binding.address.getText().toString().length() ==0){
            return new Pair<>(enterAddress,false);
        }

        if(binding.phone.getText().toString().length() ==0){
            return new Pair<>(phone0,false);
        }

        if(!isValidPhone()){
            return new Pair<>(wrongPhone,false);
        }
        return new Pair("",true);
    }

    private boolean startsWith(){
        String phone=binding.phone.getText().toString();
        return phone.startsWith("077")||phone.startsWith("075")||phone.startsWith("079")||phone.startsWith("078");
    }

    private boolean isValidPhone(){
        if(startsWith() && binding.phone.getText().toString().length()==11){
            return true;
        }else{
            return false;
        }
    }

    private AlertDialog  createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(selectGender)
                .setPositiveButton(male, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        binding.gender.setText("MALE");
                    }
                })
                .setNegativeButton(female, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        binding.gender.setText("FEMALE");
                    }
                });

        return builder.create();
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
                if (registerViewModel!=null){
                    registerViewModel.setMutableLiveDataNull();
                }
                setupViewModel();
            }
        });
        binding.gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog dialog = createDialog();
                dialog.setOnShowListener( new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface arg0) {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                    }
                });
                dialog.show();

            }
        });
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefHelper.getInstance().setLanguage(false);
                setLanguage();
            }
        });
    }


    private void login(){

       LoginViewModel loginViewModel  = new ViewModelProvider(this, new ViewModelProvider.Factory() {
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
        loginViewModel.getLoginRepository().observe(RegisterFragment.this, new Observer<MyResponse>() {
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
                            CustomSnackView.showSnackBar(binding.registerLayout,requestError,true);
                        }
                    }
                }
        );
    }

    private void setupViewModel(){
        Pair<String,Boolean> pair=validation();
        if(pair.second){
            showProgressBar();
            registerViewModel = new ViewModelProvider(this, new ViewModelProvider.Factory() {
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
                    Long.parseLong(binding.phone.getText().toString()));

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
                                login();
                                getActivity().finish();
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
        else{
            CustomSnackView.showSnackBar(binding.registerLayout,pair.first,true);
        }
    }

    public void goToLogin(){
        LoginFragment.newInstance(getParentFragmentManager());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setLanguage();
        return binding.getRoot();
    }
}