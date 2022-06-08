package com.example.space.MusicPlayer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.space.Service.MediaService;

public class NotificationReciver extends BroadcastReceiver {
    public  static final String ACTION_PREV = "PREVIOUS";
    public  static final String ACTION_NEXT = "NEXT";
    public  static final String ACTION_PLAY = "PLAY";
    public  static final String ACTION_STOP = "STOP";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MediaService.class);
        if(intent.getAction() != null){
            switch (intent.getAction()){
                case ACTION_PLAY:
                    Toast.makeText(context, "play", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_PREV:
                    Toast.makeText(context, "prev", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_NEXT:
                    Toast.makeText(context, "next", Toast.LENGTH_SHORT).show();
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
                case ACTION_STOP:
                    intent1.putExtra("myActionName", intent.getAction());
                    context.startService(intent1);
                    break;
            }
        }
    }
}
