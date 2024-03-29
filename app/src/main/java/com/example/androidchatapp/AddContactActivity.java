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

import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Daos.MessageDao;
import com.example.androidchatapp.Daos.UserDao;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.User;
import com.example.androidchatapp.SyncTasks.AddContactTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

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
            //UsersApi usersApi = new UsersApi();
            //usersApi.addContact(username,UnameStr, NnameStr, "localhost:5200",
             //       "2022-06-11T22:01:12.998Z");
            try {
                new AddContactTask(
                        username, UnameStr, NnameStr, "localhost:5200")
                        .execute().get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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