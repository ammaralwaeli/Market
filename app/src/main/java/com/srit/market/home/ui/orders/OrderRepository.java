package com.srit.market.home.ui.orders;


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

public class OrderRepository {

    private static OrderRepository instance;
    public static OrderRepository getInstance(){
        if(instance==null){
            instance= new OrderRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private OrderRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getOrders(){

        data = new MutableLiveData<>();

        apiServices.getOrders().enqueue(new Callback<OrdersModel>() {

            @Override
            public void onResponse(@NotNull Call<OrdersModel> call, @NotNull Response<OrdersModel> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("OrderError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<OrdersModel> call, @NotNull Throwable t) {
                Log.e("OrderError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }

}
