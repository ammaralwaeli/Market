package com.srit.market.home.ui.more;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
    String title, notAuth,requestError,langMessage,changeLang;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(this);
        setLanguage();
        ((MainActivity) Objects.requireNonNull(getActivity())).setCustomTitle(title);
        binding.signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefHelper.getInstance().setAccessToken(null);
                RegisterActivity.newInstance(getContext());
                getActivity().finish();
            }
        });
        binding.changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment.newInstance(getParentFragmentManager());
            }
        });

        binding.language.setOnClickListener(new View.OnClickListener() {
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
        setupViewModel();

        return binding.getRoot();
    }

    private AlertDialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(langMessage)
                .setPositiveButton(R.string.english, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefHelper.getInstance().setLanguage(false);
                        setLanguage();
                        ((MainActivity) Objects.requireNonNull(getActivity())).setLanguage();
                    }
                })
                .setNegativeButton(R.string.arabic, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefHelper.getInstance().setLanguage(true);
                        setLanguage();
                        ((MainActivity) Objects.requireNonNull(getActivity())).setLanguage();
                    }
                });

        return builder.create();
    }

    private void setLanguage(){
        if(SharedPrefHelper.getInstance().getLanguage()){
            binding.moreLayout.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            binding.signOut.setText(getString(R.string.ar_sign_out));
            binding.changePassword.setText(getString(R.string.ar_change_password));
            title=getString(R.string.ar_more_title);
            notAuth =getString(R.string.ar_not_auth);
            requestError=getString(R.string.ar_connection_error);
            langMessage=getString(R.string.ar_lang_message);
            changeLang=getString(R.string.ar_change_lang);
            binding.language.setText(changeLang.concat(" ("+getString(R.string.arabic)+")"));
        }
        else{
            binding.moreLayout.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            title=getString(R.string.en_more_title);
            notAuth =getString(R.string.en_not_auth);
            binding.signOut.setText(getString(R.string.en_sign_out));
            binding.changePassword.setText(getString(R.string.en_change_password));
            requestError=getString(R.string.en_connection_error);
            langMessage=getString(R.string.en_lang_message);
            changeLang=getString(R.string.en_change_lang);
            binding.language.setText(changeLang.concat(" ("+getString(R.string.english)+")"));
        }
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
            CustomSnackView.showSnackBar(binding.moreLayout, notAuth, true);
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
                            CustomSnackView.showSnackBar(binding.moreLayout, requestError, true);
                        }
                        hideProgressBar();
                    }
                }
        );
    }
}