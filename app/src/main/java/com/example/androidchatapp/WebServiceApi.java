package com.example.androidchatapp;

import com.example.androidchatapp.Compatible.ContactToApi;
import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface WebServiceApi {
    @POST("register")
    Call<Void> register(@Body UserToApi user);

    @POST("LoginApi")
    Call<UserToApi> login(@Body UserToApi user);

    @GET("Contacts")
    Call<List<Contact>> contacts(@Query("userId") String userId);

    @POST("Contacts/{id}")
    Call<Void> addContact(
            @Path(value = "id", encoded = true)String id,
            @Query("userId") String userId,
                          @Body ContactToApi contactToApi);
}
