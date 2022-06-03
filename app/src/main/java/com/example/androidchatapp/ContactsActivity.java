package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;
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
    private User userObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        username = getIntent().getExtras().getString("CurUsr");
        userObject = userDao.find(username);
        lvContacts = findViewById(R.id.lvContacts);
        contacts = new ArrayList<>();
        contacts.addAll(contactDao.getAll(username));
        adapter = new ContactListAdapter(this, contacts);

        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Contact contact = contacts.remove(i);
            contactDao.delete(contact);
            adapter.notifyDataSetChanged();
            return true;
        });

        FloatingActionButton addContact = findViewById(R.id.btnAddContact);
        addContact.setOnClickListener(view -> {
            Intent i = new Intent(this, AddContactActivity.class);
            i.putExtra("CurUsr", this.username);
            startActivity(i);
        });

    }
    @Override
    protected void onResume(){
        super.onResume();
        contacts.clear();
        contacts.addAll(contactDao.getAll(this.username));
        adapter.notifyDataSetChanged();
    }
}