package com.example.androidchatapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Daos.MessageDao;
import com.example.androidchatapp.Daos.UserDao;
import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;
import com.example.androidchatapp.ViewModels.MessagesViewModel;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ChatDao chatDao;
    private ListView lvMessages;
    private MessageListAdapter adapter;
    private ArrayList<Message> messages;
    private String username;
    private String contactUsername;
    private String contactNickname;
    private User userObject;
    private Chat chat;
    private MessagesViewModel messagesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        userDao = db.userDao();
        contactDao = db.contactDao();
        messageDao = db.messageDao();
        chatDao = db.chatDao();
        username = getIntent().getExtras().getString("CurUsr");
        contactUsername = getIntent().getExtras().getString("CurContact");
        contactNickname = getIntent().getExtras().getString("CurContactNickname");
        messagesViewModel = new ViewModelProvider(this)
                .get(MessagesViewModel.class);
        messagesViewModel.getMessages().observe(this, messageList -> {
            //userObject = userDao.find(username);
            //chat = chatDao.find(username, contactUsername);
            lvMessages = findViewById(R.id.lvMessages);
            TextView tvContactName = findViewById(R.id.tvContactNameChat);
            tvContactName.setText(contactNickname);
            messages = new ArrayList<>();
            messages.addAll(messageList);
            adapter = new MessageListAdapter(this, messages);
            lvMessages.setAdapter(adapter);
        });
        /*
        userObject = userDao.find(username);
        chat = chatDao.find(username, contactUsername);
        lvMessages = findViewById(R.id.lvMessages);
        TextView tvContactName = findViewById(R.id.tvContactNameChat);
        tvContactName.setText(contactNickname);
        messages = new ArrayList<>();
        messages.addAll(messageDao.getAllMessages(chat.getId()));
        for(Message m : messages){
            if(m.getSentBy().equals(username)){
                m.setSent(true);
            }
            else{
                m.setSent(false);
            }
        }
        adapter = new MessageListAdapter(this, messages);
        lvMessages.setAdapter(adapter);
        */
        UsersApi usersApi = new UsersApi();
        usersApi.messages(username, contactUsername, this);
        EditText etMessage = findViewById(R.id.etSendMessage);
        Button btnSend = findViewById(R.id.btnSendMessage);

        btnSend.setOnClickListener(view -> {
           // Message m = new Message(etMessage.getText().toString(), true, username, chat.getId());
            usersApi.sendMessage(username, contactUsername, etMessage.getText().toString());
            // messageDao.insert(m);
            //messages.clear();
          //  messages.addAll(messageDao.getAllMessages(chat.getId()));
            /*
            for(Message mes : messages){
                if(mes.getSentBy().equals(username)){
                    mes.setSent(true);
                }
                else{
                    mes.setSent(false);
                }
            }

             */
           // adapter.notifyDataSetChanged();
        });
    }
}