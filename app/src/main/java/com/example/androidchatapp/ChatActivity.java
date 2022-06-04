package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ChatDao chatDao;
    private ListView lvMessages;
    private ArrayAdapter adapter;
    private ArrayList<Message> messages;
    private String username;
    private String contactUsername;
    private String contactNickname;
    private User userObject;
    private Chat chat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        messageDao = db.messageDao();
        chatDao = db.chatDao();
        username = getIntent().getExtras().getString("CurUsr");
        contactUsername = getIntent().getExtras().getString("CurContact");
        userObject = userDao.find(username);
        chat = chatDao.find(username, contactUsername);
        lvMessages = findViewById(R.id.lvMessages);
        messages = new ArrayList<>();
        messages.addAll(messageDao.getAllMessages(chat.getId()));
        adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, messages);
        lvMessages.setAdapter(adapter);

        EditText etMessage = findViewById(R.id.etSendMessage);
        Button btnSend = findViewById(R.id.btnSendMessage);

        btnSend.setOnClickListener(view -> {
            Message m = new Message(etMessage.getText().toString(), true, username, chat.getId());
            messageDao.insert(m);
            messages.clear();
            messages.addAll(messageDao.getAllMessages(chat.getId()));
            adapter.notifyDataSetChanged();
        });
    }
}