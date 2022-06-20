package com.example.androidchatapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Daos.MessageDao;
import com.example.androidchatapp.Daos.UserDao;
import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.Entities.User;
import com.example.androidchatapp.SyncTasks.GetContactsTask;
import com.example.androidchatapp.SyncTasks.GetMessagesTask;
import com.example.androidchatapp.SyncTasks.SendMessageTask;
import com.example.androidchatapp.ViewModels.ContactsViewModel;
import com.example.androidchatapp.ViewModels.MessagesViewModel;
import com.example.androidchatapp.ViewModels.UpdateViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ChatActivity extends AppCompatActivity {
    private AppDB db;
    private UserDao userDao;
    private ContactDao contactDao;
    private MessageDao messageDao;
    private ChatDao chatDao;
    private ArrayList<Contact> contacts;
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
    private ContactsViewModel contactsViewModel;
    private ListView lvContacts;
    private ContactListAdapter adapterContacts;
    private ContactsActivity contactsActivity;

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

        int orientation = getResources().getConfiguration().orientation;
        // In landscape
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contacts = new ArrayList<>();
            contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
            contactsViewModel.getContacts().observe(this, contactsList -> {
                this.contacts = new ArrayList<>(contactsList);
                lvContacts = findViewById(R.id.lvContacts);

                adapterContacts = new ContactListAdapter(this, contacts);

                lvContacts.setAdapter(adapterContacts);
                lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
                    Contact contact = contacts.remove(i);
                    contactDao.delete(contact);
                    adapterContacts.notifyDataSetChanged();
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


            });
            FloatingActionButton addContact = findViewById(R.id.btnAddContact);
            addContact.setOnClickListener(view -> {
                Intent i = new Intent(this, AddContactActivity.class);
                i.putExtra("CurUsr", this.username);
                startActivity(i);
            });

            this.contacts = new ArrayList<>();
            contacts.addAll(contactDao.getAll(username));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        int orientation = getResources().getConfiguration().orientation;
        // In landscape
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            contactsViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);
            contactsViewModel.getContacts().observe(this, contactsList -> {
                this.contacts = new ArrayList<>(contactsList);
                lvContacts = findViewById(R.id.lvContacts);

                adapterContacts = new ContactListAdapter(this, contacts);

                lvContacts.setAdapter(adapterContacts);
                lvContacts.setOnItemLongClickListener((adapterView, view, i, l) -> {
                    Contact contact = contacts.remove(i);
                    contactDao.delete(contact);
                    adapterContacts.notifyDataSetChanged();
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

                //new GetContactsTask(contactsViewModel, contactsActivity,
                //        contactDao, chatDao, username).execute();
            });

            FloatingActionButton addContact = findViewById(R.id.btnAddContact);
            addContact.setOnClickListener(view -> {
                Intent i = new Intent(this, AddContactActivity.class);
                i.putExtra("CurUsr", this.username);
                startActivity(i);
            });

            this.contacts = new ArrayList<>();
            contacts.addAll(contactDao.getAll(username));

        }


    }
}