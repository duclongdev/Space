package com.example.space.Service;


//import static com.example.musicapplication.ApplicationClass.CHANNEL_ID;
//import static com.example.musicapplication.List_Songs.mangsong;

import static com.example.space.MainActivity.mangsong;
import static com.example.space.MusicPlayer.ApplicationClass.CHANNEL_ID;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.Layout;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.media.session.MediaButtonReceiver;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.space.MainActivity;
import com.example.space.MusicPlayer.ActionPlaying;
import com.example.space.MusicPlayer.NotificationReciver;
import com.example.space.R;
import com.example.space.model.Song;

import java.io.IOException;
import java.util.ArrayList;

public class MediaService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private IBinder iBinder = new myBinder();
    public static final String ACTION_PREV = "PREVIOUS";
    public static final String ACTION_NEXT = "NEXT";
    public static final String ACTION_PLAY = "PLAY";
    public  static final String ACTION_STOP = "STOP";

    MediaPlayer mediaPlayer = null;
    ArrayList<Song> Songs = new ArrayList<>();
    int position = -1;
    ActionPlaying actionPlaying;
    MediaSessionCompat mediaSessionCompat;
    boolean isPause = false;
    Bitmap bitmap;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("e", "e");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    public class myBinder extends Binder {
        public MediaService getService() {
            return MediaService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("d", "d");
        int myPosition = intent.getIntExtra("servicePosition", -1);
        if (myPosition != -1)
            playMedia(myPosition);
        String actionName = intent.getStringExtra("myActionName");
        if (actionName != null) {
            switch (actionName) {
                case ACTION_PLAY:
                    if (actionPlaying != null)
                        actionPlaying.playClick();
                    break;
                case ACTION_PREV:
                    if (actionPlaying != null)
                        actionPlaying.prevClick();
                    break;
                case ACTION_NEXT:
                    if (actionPlaying != null)
                        actionPlaying.nextClick();
                    break;
                case ACTION_STOP:
                    stopService(new Intent(getBaseContext(), MediaService.class));
                    break;
            }
        }

        return START_STICKY;
    }

    public void setLoop() {
        mediaPlayer.setLooping(false);
    }

    private void playMedia(int startPosition) {
        Songs = mangsong;
        position = startPosition;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            if (Songs != null) {
                createMediaPlayer(position, false, false);
                mediaPlayer.start();
            }
        } else {
            createMediaPlayer(position, false, false);
            mediaPlayer.start();
            Log.e("c", "c");
        }
    }

    public void setCallback(ActionPlaying actionPlaying) {
        this.actionPlaying = actionPlaying;
    }

    public void start() {
        mediaPlayer.start();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void stop() {
        mediaPlayer.release();
    }

    public void release() {
        mediaPlayer.release();
    }

    public void reset() {
        mediaPlayer.reset();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setLooping(boolean is) {
        mediaPlayer.setLooping(is);
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public void seekTo(int position) {
        mediaPlayer.seekTo(position);
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    //    public void createMediaPlayer(int position) {
//        Log.e("b", "b");
//        mediaPlayer = new MediaPlayer();
////        Log.e("media1", String.valueOf(mediaPlayer));
//        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//        this.position = position;
//
////        mediaPlayer = MediaPlayer.create(getBaseContext(),Songs.get(position).getFile());
//        try {
//            mediaPlayer.setDataSource(Songs.get(position).getLinkMp3());
//            mediaPlayer.setOnPreparedListener(this);
//            mediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void createMediaPlayer(int position, boolean isloop, boolean isPause) {
        this.isPause = isPause;
        Log.e("b", "b");
        mediaPlayer = new MediaPlayer();
//        Log.e("media1", String.valueOf(mediaPlayer));
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        this.position = position;

//        mediaPlayer = MediaPlayer.create(getBaseContext(),Songs.get(position).getFile());
        try {
            mediaPlayer.setDataSource(Songs.get(position).getLinkMp3());
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            if (isloop)
                mediaPlayer.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer1) {
//        Bitmap bitmap = getBitmapFromURL(mangsong.get(position).getLinkImage());
        showNotification(R.drawable.ic_baseline_pause_24, null);
        Log.e("ngy", "ngu");
        mediaPlayer1.start();
        OnCompleted();
        if (this.isPause && mediaPlayer1 != null) {
            mediaPlayer1.pause();
        }
//        this.isPause = false;
    }

    public void OnPrepared() {
        mediaPlayer.setOnPreparedListener(this);
    }

    void OnCompleted() {
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("destroy", "destroy");
        if (mediaPlayer != null) {
            mediaPlayer.stop();
//            mediaPlayer = null;

        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer1) {
        Log.e("done", "done");
        Log.e("action", String.valueOf(actionPlaying));
        mediaPlayer1.reset();
//        createMediaPlayer(position);
        if (!mediaPlayer1.isLooping()) {
            actionPlaying.nextClick();
            Log.e("Lopp", "Loop");
        } else {
            Log.e("Lopp", "Lopp");
//            actionPlaying.playClick();
            actionPlaying.playClick();
        }
    }

    public void createPaletteSync(Bitmap bitmap, LinearLayout layout) {
//        Bitmap bitmap = drawable.getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch swatch = palette.getDominantSwatch();
                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                if (swatch != null) {
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{0xff000000,0xff000000,0xff000000,0xff000000, swatch.getRgb(), swatch.getRgb()});
                    //new int[]{swatch.getRgb(), darkMutedSwatch.getRgb(), darkMutedSwatch.getRgb()});
                    layout.setBackground(gradientDrawable);
                } else {
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{0xff000000, 0xff000000});
                    layout.setBackground(gradientDrawable);
                }
            }
        });
//        Palette palette = Palette.from(bitmap).generate();
//        return palette;
    }
    public void setImage(ImageView image) {
        Glide.with(this)
                .asBitmap()
                .load(Songs.get(position).getLinkImageS())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        image.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        if (!isPlaying())
            showNotification(R.drawable.ic_baseline_pause_24, null);
        else showNotification(R.drawable.ic_baseline_play_arrow_24, null);
    }

    public void showNotification(int play_pause, LinearLayout layout) {
        Log.e("noti", "noti");
        mediaSessionCompat = new MediaSessionCompat(getBaseContext(), "My Audio");
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Intent preIntent = new Intent(this, NotificationReciver.class).setAction(ACTION_PREV);
        PendingIntent prePendingIntent = PendingIntent.getBroadcast(this, 0, preIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent playIntent = new Intent(this, NotificationReciver.class).setAction(ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent nextIntent = new Intent(this, NotificationReciver.class).setAction(ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent stopIntent = new Intent(this, NotificationReciver.class).setAction(ACTION_STOP);
        PendingIntent stopPendingIntent = PendingIntent.getBroadcast(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        BitmapDrawable drawable  = (BitmapDrawable) drawable1;
        Bitmap bitmap = getBitmapFromURL(Songs.get(position).getLinkImageS(), layout);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID);
        notification
                .setContentTitle(Songs.get(position).getTitleSong())
                .setContentText(Songs.get(position).getName())
                .setLargeIcon(bitmap)
//                .setSubText("nguyenbakhanh")
                .setSmallIcon(R.drawable.ic_baseline_audiotrack_24)
                .setAutoCancel(true)
                // Add media control buttons that invoke intents in your media service
                .addAction(R.drawable.ic_baseline_skip_previous_24, "Previous", prePendingIntent) // #0
                .addAction(play_pause, "Pause", playPendingIntent)  // #1
                .addAction(R.drawable.ic_baseline_skip_next_24, "Next", nextPendingIntent)
                .addAction(R.drawable.ic_baseline_clear_24, "clear", stopPendingIntent)// #2
                // Apply the media style template
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
//                        .setMediaSession(mediaSessionCompat.getSessionToken())
                        .setShowActionsInCompactView(1 /* #1: pause button */)
                        .setShowCancelButton(true)
                        .setCancelButtonIntent(MediaButtonReceiver.buildMediaButtonPendingIntent(getBaseContext(),
                                PlaybackStateCompat.ACTION_STOP)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setOnlyAlertOnce(true)
                .setSilent(true)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .build();
        startForeground(11, notification.build());
    }

    public Bitmap getBitmapFromURL(String src, LinearLayout layout) {
        final Bitmap[] bitmap = new Bitmap[1];
        Glide.with(this)
                .asBitmap()
                .load(src)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        bitmap[0] = resource;
//                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        if(layout != null)
                            createPaletteSync(resource, layout);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
        return bitmap[0];
    }

}
