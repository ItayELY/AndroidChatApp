package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;

import java.util.List;

public class ContactsActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private ListView lvContacts;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
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
        contacts = contactDao.getAll(username);
        adapter = new ArrayAdapter<Contact>(this,
                android.R.layout.simple_list_item_1,
                contacts);

        lvContacts.setAdapter(adapter);
        lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Contact contact = contacts.remove(i);
            contactDao.delete(contact);
            adapter.notifyDataSetChanged();
            return true;
        });

    }
}