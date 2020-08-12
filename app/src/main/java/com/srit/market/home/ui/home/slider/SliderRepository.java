package com.srit.market.home.ui.home.slider;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.srit.market.api.ApiService;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.RetrofitService;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SliderRepository {
    private static SliderRepository instance;
    public static SliderRepository getInstance(){
        if(instance==null){
            instance= new SliderRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private SliderRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;

    MutableLiveData<MyResponse> getSlider(){

        data = new MutableLiveData<>();
        apiServices= RetrofitService.cteateService(ApiService.class);
        apiServices.getSlider().enqueue(new Callback<SliModel>() {

            @Override
            public void onResponse(@NotNull Call<SliModel> call, @NotNull Response<SliModel> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("sliderError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<SliModel> call, @NotNull Throwable t) {
                Log.e("sliderError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }
}
