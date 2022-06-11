package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Entities.Message;

import java.util.List;

public class MessagesViewModel extends ViewModel {
    private MutableLiveData<List<Message>> messages;
    public MutableLiveData<List<Message>> getMessages(){
        if(messages == null){
            messages = new MutableLiveData<List<Message>>();
        }
        return messages;
    }
}