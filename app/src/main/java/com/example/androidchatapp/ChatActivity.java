package com.example.androidchatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
import com.example.androidchatapp.SyncTasks.GetMessagesTask;
import com.example.androidchatapp.SyncTasks.SendMessageTask;
import com.example.androidchatapp.ViewModels.MessagesViewModel;
import com.example.androidchatapp.ViewModels.UpdateViewModel;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

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
    UpdateViewModel updateViewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        lvMessages = findViewById(R.id.lvMessages);
        TextView tvContactName = findViewById(R.id.tvContactNameChat);
        updateViewModel = new ViewModelProvider(this)
                .get(UpdateViewModel.class);
        updateViewModel.getUpdate().observe(this, update -> {
            new GetMessagesTask(messagesViewModel, this, chatDao,
                    messageDao, username, contactUsername).execute();
        });

        messagesViewModel = new ViewModelProvider(this)
                .get(MessagesViewModel.class);
       // FirebaseService.setViewModel(messagesViewModel, this, chatDao, messageDao, username, contactUsername);
        messagesViewModel.getMessages().observe(this, messageList -> {
            //userObject = userDao.find(username);
            //chat = chatDao.find(username, contactUsername);

            tvContactName.setText(contactNickname);
            messages = new ArrayList<>();
            messages.addAll(messageList);
            adapter = new MessageListAdapter(this, messages);
            lvMessages.setAdapter(adapter);
        });


        EditText etMessage = findViewById(R.id.etSendMessage);
        Button btnSend = findViewById(R.id.btnSendMessage);
        this.messages = new ArrayList<>();
        chat = chatDao.find(username, contactUsername);
        new GetMessagesTask(messagesViewModel, this, chatDao,
                messageDao, username, contactUsername).execute();
        messages.addAll(messageDao.getAllMessages(chat.getId()));
        adapter = new MessageListAdapter(this, messages);
        lvMessages.setAdapter(adapter);
        btnSend.setOnClickListener(view -> {
            try {
                new SendMessageTask(username, contactUsername, etMessage.getText().toString()
                , updateViewModel.getUpdate()).execute().get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
            etMessage.setText("");
        });
    }
}