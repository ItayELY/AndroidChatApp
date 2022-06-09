package com.example.androidchatapp;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Entities.User;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServiceApi {
    @POST("register")
    Call<Void> register(@Body UserToApi user);

    @POST("LoginApi")
    Call<UserToApi> login(@Body UserToApi user);
}
