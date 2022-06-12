package com.example.androidchatapp.SyncTasks;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

import com.example.androidchatapp.ChatActivity;
import com.example.androidchatapp.ContactsActivity;
import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.ContactDao;
import com.example.androidchatapp.Daos.MessageDao;
import com.example.androidchatapp.Entities.Chat;
import com.example.androidchatapp.Entities.Contact;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.UsersApi;
import com.example.androidchatapp.ViewModels.ContactsViewModel;
import com.example.androidchatapp.ViewModels.MessagesViewModel;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class GetMessagesTask extends AsyncTask<Void, Void, Void> {
    private MessagesViewModel messagesViewModel;
    private ChatActivity chatActivity;
    private ChatDao cdao;
    private MessageDao mdao;
    private String username;
    private String contactId;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public GetMessagesTask(MessagesViewModel messagesViewModel,
                           ChatActivity chatActivity,
                           ChatDao cdao, MessageDao mdao,
                           String username,
                           String contactId){
        this.messagesViewModel = messagesViewModel;
        this.username = username;
        this.contactId = contactId;
        this.cdao = cdao;
        this.mdao = mdao;
        this.chatActivity = chatActivity;
        messagesViewModel.getMessages().observe(this.chatActivity, messagesList -> {
            Chat chat = this.cdao.find(username, contactId);

            List<Message> messages = this.mdao.getAllMessages(chat.getId());

            LocalDateTime maxDate = messages.stream()
                    .map(Message::getCreated).max(LocalDateTime::compareTo).get();

            for (Message m : messagesList){
                if(m.getCreated().isAfter(maxDate)){
                    this.mdao.insert(m);
                }
            }

        });

    }


    @Override
    protected Void doInBackground(Void... voids) {

        UsersApi usersApi = new UsersApi();
        usersApi.messagesViewModel(username, contactId,
                messagesViewModel.getMessages());



        return null;
    }
}
