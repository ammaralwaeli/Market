package com.srit.market.home.ui.more;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.srit.market.api.ApiService;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.RetrofitService;
import com.srit.market.home.ui.home.category.CatModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileRepository {

    private static ProfileRepository instance;
    public static ProfileRepository getInstance(){
        if(instance==null){
            instance= new ProfileRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private ProfileRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getProfile(){

        data = new MutableLiveData<>();

        apiServices.getProfile().enqueue(new Callback<Profile>() {

            @Override
            public void onResponse(@NotNull Call<Profile> call, @NotNull Response<Profile> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("ProfileError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Profile> call, @NotNull Throwable t) {
                Log.e("ProfileError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }


}
