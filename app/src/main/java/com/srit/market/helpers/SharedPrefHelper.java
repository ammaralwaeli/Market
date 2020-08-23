package com.srit.market.helpers;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefHelper {
    private static SharedPrefHelper instance;

    private static String PREF_NAME = "mySettingsPref";
    private static String ACCESS_TOKEN = "ACCESS_TOKEN";
    private static String LANGUAGE = "LANGUAGE";

    public static void init(Context context) {
        instance= new SharedPrefHelper(context);
    }


    public static SharedPrefHelper getInstance(){
        if(instance == null) throw new NullPointerException();
        return instance;
    }

    private SharedPreferences mSharedPreferences;

    private SharedPrefHelper(Context context){
        mSharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }


    public boolean getLanguage() {
        return mSharedPreferences.getBoolean(LANGUAGE,false);
    }

    public void setLanguage(boolean isRegister) {
        mSharedPreferences.edit().putBoolean(LANGUAGE, isRegister).apply();
    }

    public String getAccessToken(){
        return mSharedPreferences.getString(ACCESS_TOKEN, null);
    }

    public void setAccessToken(String accessToken){
        mSharedPreferences.edit().putString(ACCESS_TOKEN, accessToken).apply();
    }

}
