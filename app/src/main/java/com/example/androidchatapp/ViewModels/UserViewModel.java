package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;

public class UserViewModel extends ViewModel {
    private MutableLiveData<UserToApi> user;
    public MutableLiveData<UserToApi> getUser(){
        if(user == null){
            user = new MutableLiveData<UserToApi>();
        }
        return user;
    }

}
