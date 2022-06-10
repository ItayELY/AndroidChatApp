package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Entities.Contact;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private MutableLiveData<List<Contact>> contacts;
    public MutableLiveData<List<Contact>> getContacts(){
        if(contacts == null){
            contacts = new MutableLiveData<List<Contact>>();
        }
        return contacts;
    }
}
