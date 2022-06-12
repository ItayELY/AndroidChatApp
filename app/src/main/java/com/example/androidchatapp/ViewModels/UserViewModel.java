package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.UsersApi;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserToApi> user;
    public MutableLiveData<UserToApi> getUser(){
        if(user == null){
            user = new MutableLiveData<UserToApi>();
        }
        return user;
    }

    public void login(String username, String password){
        UsersApi usersApi = new UsersApi();
        usersApi.loginViewModel(username, password, user);
    }

}
