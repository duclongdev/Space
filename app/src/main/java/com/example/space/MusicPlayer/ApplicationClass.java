package com.example.space.MusicPlayer;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;


public class ApplicationClass extends Application {
    public static final String CHANNEL_ID = "CHANNEL_MUSIC_APP";
    public static final String ACTION_PREV = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PLAY = "PLAY";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotification();
    }

    private void createNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel music", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(channel);
        }
    }
}
