package com.srit.market.api;

import com.google.gson.JsonObject;
import com.srit.market.home.ui.home.category.CatModel;
import com.srit.market.home.ui.home.item.Items;
import com.srit.market.home.ui.home.item.PostCategoryID;
import com.srit.market.home.ui.home.slider.SliModel;
import com.srit.market.home.ui.more.Profile;
import com.srit.market.home.ui.more.change_password.ChangePasswordModel;
import com.srit.market.home.ui.new_order.OrderPostModel;
import com.srit.market.home.ui.orders.OrdersModel;
import com.srit.market.home.ui.orders.order_details.AllOrders;
import com.srit.market.register.login.LoginModel;
import com.srit.market.register.register.RegisterModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

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

    @POST("/orders/addorder")
    Call<JsonObject> addOrder(@Body OrderPostModel orderPostModel);

    @PUT("/users/changepassword")
    Call<JsonObject> changePassword(@Body ChangePasswordModel changePasswordModel);


    @GET("/orders/getorderitems")
    Call<AllOrders> getOrderItems(@Query("orderID") int orderID);
}
