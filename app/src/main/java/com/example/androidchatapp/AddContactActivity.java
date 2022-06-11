package com.example.androidchatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;

import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddContactActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private ChatDao chatDao;
    private MessageDao messageDao;
    private ListView lvContacts;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contacts;
    private String username;
    private User userObject;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        chatDao = db.chatDao();
        messageDao = db.messageDao();
        username = getIntent().getExtras().getString("CurUsr");
        //userObject = userDao.find(username);

        Button Add = findViewById(R.id.btnSubmitNewContact);

        Add.setOnClickListener(view -> {
            EditText Uname = findViewById(R.id.etNewContactUsername);
            String UnameStr = Uname.getText().toString();
            EditText Nname = findViewById(R.id.etNewContactNickname);
            String NnameStr = Nname.getText().toString();
            UsersApi usersApi = new UsersApi();
            usersApi.addContact(username,UnameStr, NnameStr, "localhost:3000",
                    "2022-06-11T22:01:12.998Z");
            /*
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
            Chat c1 = chatDao.find(username, UnameStr);
            if(c1 != null){
                finish();
            }
            Chat c = new Chat(username, UnameStr);
            Message m = new Message("hi", true, username,
                    c.getId());
            chatDao.insert(c);
            messageDao.insert(m);

             */
            finish();
        });
    }
}