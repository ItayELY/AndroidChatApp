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

public class GetContactsTask extends AsyncTask<Void, Void, Void> {
    private ContactsViewModel contactsViewModel;
    private ContactsActivity contactsActivity;
    private ContactDao codao;
    private ChatDao chdao;
    private String username;

    public GetContactsTask(ContactsViewModel contactsViewModel,
                           ContactsActivity contactsActivity,
                           ContactDao codao, ChatDao chdao, String username){
        this.contactsViewModel = contactsViewModel;

        this.codao = codao;
        this.chdao = chdao;
        contactsViewModel.getContacts().observe(contactsActivity, contacts -> {
            List<Contact> curContacts = codao.getAll(username);

            for(Contact c : contacts){
                if(codao.get(c.getUserId(), c.getId()) == null){
                    codao.insert(c);
                }
                Chat ch = chdao.find(username, c.getId());
                if(ch == null){
                    ch = new Chat(username, c.getId());
                    chdao.insert(ch);
                }
            }
        });
        this.username = username;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        UsersApi usersApi = new UsersApi();
        usersApi.contactsViewModel(username, contactsViewModel.getContacts());

        return null;
    }
}
