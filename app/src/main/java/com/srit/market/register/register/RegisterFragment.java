package com.srit.market.register.register;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
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

import org.w3c.dom.Text;

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


    private Pair<String,Boolean> validation(){
        if(binding.username.getText().toString().length()<8){
            return new Pair<>("Username must be over than 8 chars",false);
        }

        if(binding.username.getText().toString().length()==0){
            return new Pair<>("Please, Enter your username",false);
        }
        if(binding.password.getText().toString().length() <6){
            return new Pair<>("Password must be over than 6 chars",false);
        }

        if(binding.password.getText().toString().length() ==0){
            return new Pair<>("Please, Enter your password",false);
        }

        if(binding.gender.getText().toString().length() ==0){
            return new Pair<>("Please, Select your gender",false);
        }

        if(binding.address.getText().toString().length() ==0){
            return new Pair<>("Please, Enter your address",false);
        }

        if(binding.phone.getText().toString().length() ==0){
            return new Pair<>("Please, Enter your Phone",false);
        }

        if(!isValidPhone()){
            return new Pair<>("Please, Enter your Phone in this form 07*********",false);
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
        builder.setMessage(R.string.dialog_gender_missiles)
                .setPositiveButton("MALE", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        binding.gender.setText("MALE");
                    }
                })
                .setNegativeButton("FEMALE", new DialogInterface.OnClickListener() {
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
    }




    private void setupViewModel(){
        Pair<String,Boolean> pair=validation();
        if(pair.second){
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
        else{
            CustomSnackView.showSnackBar(binding.registerLayout,pair.first,true);
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