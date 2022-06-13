package com.example.androidchatapp.SyncTasks;

import android.os.AsyncTask;

import androidx.lifecycle.MutableLiveData;

import com.example.androidchatapp.ContactsActivity;
import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.UsersApi;
import com.example.androidchatapp.ViewModels.ContactsViewModel;

import java.util.List;

public class AddContactTask extends AsyncTask<Void, Void, Void> {

    private String username;
    private String contactUserName;
    private String contactNickName;
    private String server;


    public AddContactTask(
                          String username,
                          String contactUserName,
                          String contactNickName,
                          String server) {

        this.username = username;
        this.contactUserName = contactUserName;
        this.contactNickName = contactNickName;
        this.server = server;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        UsersApi usersApi = new UsersApi();
        usersApi.addContact(username, contactUserName, contactNickName,
                server, "");

        return null;
    }
}
