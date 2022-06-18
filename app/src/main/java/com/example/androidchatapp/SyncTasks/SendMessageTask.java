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

public class SendMessageTask extends AsyncTask<Void, Void, Void> {

    private String username;
    private String contactUserName;
    private String content;
    //
    private MutableLiveData<Integer> update;


    public SendMessageTask(
            String username,
            String contactUserName,
            String content,
            MutableLiveData<Integer> update) {

        this.username = username;
        this.contactUserName = contactUserName;
        this.content = content;
        this.update = update;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        UsersApi usersApi = new UsersApi();
        usersApi.sendMessage(username, contactUserName, content, update);
        return null;
    }
}
