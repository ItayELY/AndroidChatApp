package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.UsersApi;

public class UpdateViewModel extends ViewModel {
    private MutableLiveData<Integer> update;
    public MutableLiveData<Integer> getUpdate(){
        if(update == null){
            update = new MutableLiveData<Integer>();
            update.setValue(0);
        }
        return update;
    }
}
