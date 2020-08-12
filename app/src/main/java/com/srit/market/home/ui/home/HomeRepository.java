package com.srit.market.home.ui.home;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.srit.market.api.ApiService;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.RetrofitService;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.slider.SliModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeRepository {

    private static HomeRepository instance;
    public static HomeRepository getInstance(){
        if(instance==null){
            instance= new HomeRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private HomeRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getCategory(){

        data = new MutableLiveData<>();

        apiServices.getCategory().enqueue(new Callback<CatModel>() {

            @Override
            public void onResponse(@NotNull Call<CatModel> call, @NotNull Response<CatModel> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("categoryError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CatModel> call, @NotNull Throwable t) {
                Log.e("categoryError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }


}
