package com.srit.market.helpers;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitService {

    private static String BASE_URL="http://api.market.srit.me";
    private static Gson gson=new GsonBuilder().setLenient().create();

    public static Gson getGson() {
        return gson;
    }

    public static String getBaseUrl(){
        return BASE_URL;
    }

    private static Retrofit.Builder mBuilder=new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(
                    GsonConverterFactory.create(gson)
            );

    private static Retrofit retrofit =
            mBuilder.client(NetworkHandler.getUnsafeOkHttpClient())
            .build();

    private static OkHttpClient.Builder mHttpClientWithInterceptor =new OkHttpClient.Builder();


    private static Retrofit retrofitWithAuth(){

        if(mHttpClientWithInterceptor.interceptors().size()==0){
            mHttpClientWithInterceptor.addInterceptor(
                    new Interceptor() {
                        @NotNull
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            String token =SharedPrefHelper.getInstance().getAccessToken();
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Bearer "+token)
                                    .build();
                            return chain.proceed(newRequest);
                        }
                    }
            );

        }

        return mBuilder
                .client(mHttpClientWithInterceptor.build())
                .build();
    }

    public static <S> S cteateService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public static <S> S cteateServiceWithAuth(Class<S> serviceClass) {
        return retrofitWithAuth().create(serviceClass);
    }

}
