package com.example.androidchatapp;

import static android.content.ContentValues.TAG;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseService extends FirebaseMessagingService {
    public FirebaseService() {
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
      if (remoteMessage.getNotification() != null){

          createNotificationChanel();

          NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"1")
                  .setSmallIcon(R.drawable.ic_action_name)
                  .setContentTitle(remoteMessage.getNotification().getTitle())
                  .setContentText(remoteMessage.getNotification().getBody())
                  .setPriority(NotificationCompat.PRIORITY_DEFAULT);

          NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
          notificationManager.notify(1,builder.build());
      }
    }

    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel chanel = new NotificationChannel("1", "My chanel",importance);
            chanel.setDescription("Demo chanel");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(chanel);
        }
    }
}