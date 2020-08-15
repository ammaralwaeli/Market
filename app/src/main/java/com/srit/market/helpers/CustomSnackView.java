package com.srit.market.helpers;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class CustomSnackView {
    public static void showSnackBar(ViewGroup viewGroup,String msg,boolean isError){
        Snackbar snackbar=Snackbar.make(viewGroup,msg,Snackbar.LENGTH_LONG);
        ViewGroup snackView=(ViewGroup)snackbar.getView();
        snackView.setBackgroundColor(Color.WHITE);

        if (isError){
            snackbar.setTextColor(Color.RED);
        }else{
            snackbar.setTextColor(Color.BLACK);
        }
        snackbar.show();
    }
}
