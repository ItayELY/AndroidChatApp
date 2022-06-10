package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;
import com.example.androidchatapp.ViewModels.ContactsViewModel;
import com.example.androidchatapp.ViewModels.UserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
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
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        contacts = new ArrayList<>();
        username = getIntent().getExtras().getString("CurUsr");
        contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
        contactsViewModel.getContacts().observe(this, contactsList -> {
            this.contacts = new ArrayList<>(contactsList);
            lvContacts = findViewById(R.id.lvContacts);
            //this.contacts = new ArrayList<>();
            //contacts.addAll(contactDao.getAll(username));
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
        UsersApi usersApi = new UsersApi();
        usersApi.contacts(username, this);

    }
    @Override
    protected void onResume(){
        super.onResume();
        //contacts.clear();
       // contacts.addAll(contactDao.getAll(this.username));
        //adapter.notifyDataSetChanged();
    }
}