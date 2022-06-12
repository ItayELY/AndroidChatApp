package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Daos.UserDao;
import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.SyncTasks.GetContactsTask;
import com.example.androidchatapp.ViewModels.ContactsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private ChatDao chatDao;
    private ListView lvContacts;
    private ContactListAdapter adapter;
    private ArrayList<Contact> contacts;
    private String username;
    private ContactsViewModel contactsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        chatDao = db.chatDao();
        contacts = new ArrayList<>();
        username = getIntent().getExtras().getString("CurUsr");
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.getContacts().observe(this, contactsList -> {
            this.contacts = new ArrayList<>(contactsList);
            lvContacts = findViewById(R.id.lvContacts);

            adapter = new ContactListAdapter(this, contacts);

            lvContacts.setAdapter(adapter);
            lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
                Contact contact = contacts.remove(i);
                contactDao.delete(contact);
                adapter.notifyDataSetChanged();
                return true;
            });

            lvContacts.setOnItemClickListener((adapterView, view, i, l) -> {
                Contact contact = contacts.get(i);
                Chat chat = chatDao.find(username, contact.getId());

                Intent in = new Intent(this, ChatActivity.class);

                in.putExtra("CurUsr", username);
                in.putExtra("CurContact", contact.getId());
                in.putExtra("CurContactNickname", contact.getName());
                startActivity(in);
            });

            FloatingActionButton addContact = findViewById(R.id.btnAddContact);
            addContact.setOnClickListener(view -> {
                Intent i = new Intent(this, AddContactActivity.class);
                i.putExtra("CurUsr", this.username);
                startActivity(i);
            });
        });
        /*
        UsersApi usersApi = new UsersApi();
        usersApi.contacts(username, this);

         */
        this.contacts = new ArrayList<>();
        contacts.addAll(contactDao.getAll(username));
        new GetContactsTask(contactsViewModel, this,
                contactDao, chatDao,username).execute();


    }
    @Override
    protected void onResume(){
        super.onResume();
        UsersApi usersApi = new UsersApi();
        usersApi.contacts(username, this);
    }
}