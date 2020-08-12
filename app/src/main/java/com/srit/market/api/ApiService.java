package com.srit.market.api;

import com.google.gson.JsonObject;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.item.Items;
import com.srit.market.home.ui.home.item.PostCategoryID;
import com.srit.market.home.ui.home.slider.SliModel;
import com.srit.market.home.ui.more.Profile;
import com.srit.market.home.ui.orders.OrdersModel;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.register.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/users/userlogin")
    Call<JsonObject> login(@Body LoginModel loginModel);

    @GET("/category/getCategory")
    Call<CatModel> getCategory();

    @GET("/slider/getslider")
    Call<SliModel> getSlider();

    @GET("/orders/getuserorders")
    Call<OrdersModel> getOrders();

    @GET("/profile/getProfile")
    Call<Profile> getProfile();

    @POST("/users/adduser")
    Call<JsonObject> register(@Body RegisterModel registerModel);

    @POST("/items/getitems")
    Call<Items> getItems(@Body PostCategoryID categoryID);
}
