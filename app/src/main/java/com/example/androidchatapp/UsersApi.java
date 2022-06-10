package com.example.androidchatapp;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;
import com.example.androidchatapp.ViewModels.ContactsViewModel;
import com.example.androidchatapp.ViewModels.UserViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersApi {

    Retrofit retrofit;
    WebServiceApi webServiceApi;

    public UsersApi(){
        retrofit = new Retrofit.Builder()
                .baseUrl(MyApplication.context.getString(R.string.BaseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceApi = retrofit.create(WebServiceApi.class);
    }

    public void register(User user){
        Call<Void> call = webServiceApi.register(new UserToApi(user.getName(), user.getName()
            , user.getPassword()));
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("onReg", "regged!");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public UserToApi login(String username, String password, LoginActivity loginActivity){
        Call<UserToApi> call = webServiceApi.login(new UserToApi(username,
                username, password, "localhost:3000"));
        final Boolean[] didLogin = {false};
        final UserToApi[] resUser = {null};
        call.enqueue(new Callback<UserToApi>() {
            @Override
            public void onResponse(Call<UserToApi> call, Response<UserToApi> response) {
                didLogin[0] = (response.code() == 200);
                if(didLogin[0]){
                    UserViewModel userViewModel =
                            new ViewModelProvider(loginActivity).get(UserViewModel.class);
                    userViewModel.getUser().setValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<UserToApi> call, Throwable t) {
                Log.i("login", "fail");
            }
        });
        return resUser[0];
    }
    public void contacts(String username, ContactsActivity contactsActivity){
        Call<List<Contact>> call = webServiceApi.contacts(username);
        call.enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();
                ContactsViewModel contactsViewModel = new ViewModelProvider(contactsActivity)
                        .get(ContactsViewModel.class);
                contactsViewModel.getContacts().setValue(contacts);
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.i("contacts", t.getMessage());
            }
        });
    }
}
