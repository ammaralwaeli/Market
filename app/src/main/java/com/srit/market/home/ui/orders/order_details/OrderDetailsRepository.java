package com.srit.market.home.ui.orders.order_details;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.srit.market.api.ApiService;
import com.srit.market.helpers.MyResponse;
import com.srit.market.helpers.RetrofitService;
import com.srit.market.register.login.LoginModel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderDetailsRepository {

    private static OrderDetailsRepository instance;
    public static OrderDetailsRepository getInstance(){
        if(instance==null){
            instance= new OrderDetailsRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private OrderDetailsRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getOrderDetails(int orderId){

        data = new MutableLiveData<>();

        apiServices.getOrderItems(orderId).enqueue(new Callback<AllOrders>() {

            @Override
            public void onResponse(@NotNull Call<AllOrders> call, @NotNull Response<AllOrders> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("loginError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<AllOrders> call, @NotNull Throwable t) {
                Log.e("loginError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }
}
