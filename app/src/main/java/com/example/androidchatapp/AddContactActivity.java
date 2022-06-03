package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;

import java.util.List;

public class AddContactActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_add_contact);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        username = getIntent().getExtras().getString("CurUsr");
        userObject = userDao.find(username);

        Button Add = findViewById(R.id.btnSubmitNewContact);

        Add.setOnClickListener(view -> {
            EditText Uname = findViewById(R.id.etNewContactUsername);
            String UnameStr = Uname.getText().toString();
            EditText Nname = findViewById(R.id.etNewContactNickname);
            String NnameStr = Nname.getText().toString();
            User usr = userDao.find(UnameStr);
            if(usr == null){
                return;
            }
            Contact checkContact = contactDao.get(username, UnameStr);
            if(checkContact != null){
                return;
            }
            if(UnameStr == username){
                return;
            }
            Contact contact = new Contact(username,
                    UnameStr,
                    NnameStr,
                    "Localhost:3000"
                    , null);
            contactDao.insert(contact);

            finish();
        });
    }
}