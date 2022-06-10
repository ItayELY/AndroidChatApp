package com.example.androidchatapp.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.example.androidchatapp.ContactDao;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.UsersApi;
import com.example.androidchatapp.ViewModels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepository{
    private ContactDao contactDao;
    private UsersApi usersApi;
    private UserViewModel userViewModel;
    private ContactsListData contactsListData;
    private String userName;
    public ContactsRepository(String userName){
        this.userName = userName;
    }
    class ContactsListData extends MutableLiveData<List<Contact>>{
        public ContactsListData(){
            super();
            setValue(new ArrayList<Contact>());
        }
        @Override
        protected void onActive(){
            super.onActive();

            new Thread(() -> {
                contactsListData.postValue(contactDao.getAll(userName));

            }).start();
        }
    }
}
