package com.example.androidchatapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;

import com.example.androidchatapp.Daos.ChatDao;
import com.example.androidchatapp.Daos.MessageDao;
import com.example.androidchatapp.Entities.Message;
import com.example.androidchatapp.SyncTasks.GetMessagesTask;
import com.example.androidchatapp.ViewModels.MessagesViewModel;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirebaseService extends FirebaseMessagingService {
    public FirebaseService() {
    }

    static String tokenFirebase;
    static MessagesViewModel messagesViewModel;
    static MessageListAdapter adapter;
    static ChatActivity activity;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void setViewModel(MessagesViewModel messagesViewModel, ChatActivity chatActivity,
                                    ChatDao chatDao, MessageDao messageDao, String username, String contactId) {
        FirebaseService.messagesViewModel = messagesViewModel;
        FirebaseService.messagesViewModel.getMessages().observe(chatActivity, messages -> {
            new GetMessagesTask(messagesViewModel, chatActivity, chatDao, messageDao, username, contactId)
                    .execute();
        });

    }

    public static String getTokenFirebase() {
        return tokenFirebase;
    }

    public static void setTokenFirebase(String tokenFirebase) {
        FirebaseService.tokenFirebase = tokenFirebase;
    }

    @SuppressLint("WrongThread")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        AppDB db = Room.databaseBuilder(getApplicationContext(), AppDB.class, "UsersDB")
                .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        ChatDao chatDao = db.chatDao();
        MessageDao messageDao = db.messageDao();
        Date date = new Date(remoteMessage.getSentTime());
        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yy");
        String dateText = df2.format(date);
        Message m = new Message(remoteMessage.getNotification().getBody(), false,
                remoteMessage.getFrom(), chatDao.find(remoteMessage.getFrom(), remoteMessage.getTo()).getId(),
                dateText);
        List<Message> messages = new ArrayList<>();
        messages.add(m);
        //int a = 1;
        if (remoteMessage.getNotification() != null) {

            createNotificationChanel();
            FirebaseService.messagesViewModel.getMessages().setValue(messages);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setSmallIcon(R.drawable.ic_action_name)
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1, builder.build());
        }

        messageDao.insert(m);

        if (adapter != null && activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.addMessage(m);
                }
            });
        }

    }

    private void createNotificationChanel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("1", "My chanel", importance);
            chanel.setDescription("Demo chanel");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }
    }
}