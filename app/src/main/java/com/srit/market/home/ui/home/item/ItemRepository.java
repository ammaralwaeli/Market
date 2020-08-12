package com.srit.market.home.ui.home.item;

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


public class ItemRepository {

    private static ItemRepository instance;
    public static ItemRepository getInstance(){
        if(instance==null){
            instance= new ItemRepository();
        }
        return instance;
    }

    private ApiService apiServices;
    private ItemRepository(){
        apiServices= RetrofitService.cteateServiceWithAuth(ApiService.class);
    }

    private MutableLiveData<MyResponse> data;
    MutableLiveData<MyResponse> getItems(int catId){

        data = new MutableLiveData<>();

        apiServices.getItems(new PostCategoryID(catId)).enqueue(new Callback<Items>() {

            @Override
            public void onResponse(@NotNull Call<Items> call, @NotNull Response<Items> response) {
                if(response.isSuccessful()){
                    data.setValue(new MyResponse(response.body()));
                }else {
                    data.setValue(new MyResponse(response.code()+"  "+response.message()));
                    Log.e("categoryError",response.code()+"  "+response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<Items> call, @NotNull Throwable t) {
                Log.e("categoryError", Objects.requireNonNull(t.getMessage()));
                data.setValue(new MyResponse(Objects.requireNonNull(t.getMessage())));
            }
        });
        return data;
    }


}
