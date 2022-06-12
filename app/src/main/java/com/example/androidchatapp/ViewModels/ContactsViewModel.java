package com.example.androidchatapp.ViewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidchatapp.Compatible.UserToApi;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.UsersApi;

import java.util.List;

public class ContactsViewModel extends ViewModel {
    private MutableLiveData<List<Contact>> contacts;
    private ContactDao dao;
    public MutableLiveData<List<Contact>> getContacts(){
        if(contacts == null){
            contacts = new MutableLiveData<List<Contact>>();
        }
        return contacts;
    }

    public void contacts(String username){
        UsersApi usersApi = new UsersApi();
        usersApi.contactsViewModel(username, this.contacts);
    }
}
