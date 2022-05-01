package com.example.space.MusicPlayer;

import static android.content.Context.BIND_AUTO_CREATE;

import static com.example.space.MainActivity.mangsong;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.Service.MediaService;
import com.example.space.model.Song;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;


public class MusicPlayer extends Fragment  implements ActionPlaying, ServiceConnection {

    ImageButton prev, next, btnloop, shuffle;
    TextView curTime, ovTime, name, author;
    FloatingActionButton play, noti;
    SeekBar seekBar;
    ImageView favorite, imageView;
    private Runnable runnable;
    private MediaController mediaController;
    private AudioManager audioManager;
    private Handler handler = new Handler();
    int currentindex = 0;
    static ArrayList<Song> ListSongs = new ArrayList<>();
    private Thread playThread, prevThread, nextThread;
    MediaService mediaService;
    public MusicPlayer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        initViews(view);
//        Intent intent = getIntent();
//        currentindex = intent.getIntExtra("position",0);
        currentindex = getArguments().getInt("data"); // đây là thứ bạn cần :D
        getIntentMethod();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (mediaService != null && b)
                    mediaService.seekTo(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaService != null) {
                    int mCurrentPosition = mediaService.getCurrentPosition();
                    seekBar.setMax(mediaService.getDuration());
                    seekBar.setProgress(mCurrentPosition);
                    curTime.setText(createTime(mCurrentPosition));
                    ovTime.setText(createTime(mediaService.getDuration()));
                }
                handler.postDelayed(this, 1000);
            }
        });
        btnloop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mediaService.isLooping()){
                    btnloop.setImageResource(R.drawable.ic_baseline_shuffle_24);
                    mediaService.setLooping(true);
                }
                else {
                    btnloop.setImageResource(R.drawable.ic_baseline_repeat_24);
                    mediaService.setLooping(false);
                }

            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(ImageViewCompat.getImageTintList(shuffle).getDefaultColor() == (int) R.color.white)
//                    DrawableCompat.setTint(shuffle.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.black));
//                else{
//                    DrawableCompat.setTint(shuffle.getDrawable(), ContextCompat.getColor(getApplicationContext(), R.color.white));
//                }
//                random(ListSongs.size(), 0, ListSongs.size()-1);
            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(favorite.getDrawable().getConstantState() == getDrawable(R.drawable.ic_heart_regular).getConstantState())
//                    favorite.setImageResource(R.drawable.ic_heart_solid);
//                else{
//                    favorite.setImageResource(R.drawable.ic_heart_regular);
//                }
            }
        });
        return view;
    }
    @Override
    public void onResume() {
        Intent intent = new Intent(getActivity(), MediaService.class);
        getActivity().bindService(intent, this, BIND_AUTO_CREATE);
        playThreadbtn();
        nextThreadbtn();
        prevThreadbtn();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unbindService(this);
    }

    private void prevThreadbtn() {
        prevThread = new Thread() {
            @Override
            public void run() {
                super.run();
                prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevClick();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void nextThreadbtn() {
        nextThread = new Thread() {
            @Override
            public void run() {
                super.run();
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextClick();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void playThreadbtn() {
        playThread = new Thread() {
            @Override
            public void run() {
                super.run();
                play.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playClick();
                    }
                });
            }
        };
        playThread.start();
    }

    public void prevClick() {
        if (mediaService.isPlaying()) {
            if (currentindex > 0)
                currentindex--;
            else {
                currentindex = ListSongs.size() - 1;
            }
            seekBar.setProgress(0);
            mediaService.stop();
            mediaService.release();
            mediaService.createMediaPlayer(currentindex);
            seekBar.setMax(mediaService.getDuration());
            name.setText(ListSongs.get(currentindex).getTitleSong());
//            author.setText(ListSongs.get(currentindex).getIdArtist());
            ovTime.setText(createTime(mediaService.getDuration()));
            Glide.with(this).load(ListSongs.get(currentindex).getLinkImage()).into(imageView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
//            mediaService.OnCompleted();
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
            play.setImageResource(R.drawable.ic_baseline_pause_24);
//            mediaService.start();
        } else {
            if (currentindex > 0)
                currentindex--;
            else {
                currentindex = ListSongs.size() - 1;
            }
            seekBar.setProgress(0);
            mediaService.stop();
            mediaService.release();
            mediaService.createMediaPlayer(currentindex);
            seekBar.setMax(mediaService.getDuration());
            name.setText(ListSongs.get(currentindex).getTitleSong());
//            author.setText(ListSongs.get(currentindex).getIdArtist());
            ovTime.setText(createTime(mediaService.getDuration()));
            Glide.with(this).load(ListSongs.get(currentindex).getLinkImage()).into(imageView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
            play.setImageResource(R.drawable.ic_baseline_pause_24);
//            mediaService.OnCompleted();
        }
    }

    @Override
    public void nextClick() {
        if (mediaService.isPlaying()) {
//            mediaService.stop();
            mediaService.reset();
            if (currentindex < ListSongs.size() - 1)
                currentindex++;
            else {
                currentindex = 0;
            }
            mediaService.createMediaPlayer(currentindex);
            seekBar.setMax(mediaService.getDuration());
            name.setText(ListSongs.get(currentindex).getTitleSong());
//            author.setText(ListSongs.get(currentindex).getIdArtist());
            ovTime.setText(createTime(mediaService.getDuration()));
            Glide.with(this).load(ListSongs.get(currentindex).getLinkImage()).into(imageView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
//                        int duration = mediaService.getDuration();
                        seekBar.setProgress(mCurrentPosition);
                        seekBar.setMax(mediaService.getDuration());

//                        Log.e("duration", String.valueOf(duration));
//                        Log.e("mduration", String.valueOf(mCurrentPosition));
//                        if(mCurrentPosition > mediaService.getDuration())
//                            mediaService.OnCompleted();
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
            play.setImageResource(R.drawable.ic_baseline_pause_24);
//            mediaService.OnCompleted();
//            mediaService.start();
        } else {
//            mediaService.stop();
            mediaService.reset();
            if (currentindex < ListSongs.size() - 1)
                currentindex++;
            else {
                currentindex = 0;
            }
            mediaService.createMediaPlayer(currentindex);
            seekBar.setMax(mediaService.getDuration());
            name.setText(ListSongs.get(currentindex).getTitleSong());
//            author.setText(ListSongs.get(currentindex).getIdArtist());
            Glide.with(this).load(ListSongs.get(currentindex).getLinkImage()).into(imageView);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
                        int duration = mediaService.getDuration();
                        seekBar.setMax(duration);
                        seekBar.setProgress(mCurrentPosition);
//                        Log.e("duration", String.valueOf(duration));
//                        Log.e("mduration", String.valueOf(mCurrentPosition));

//                        if(mCurrentPosition > mediaService.getDuration())
//                            mediaService.OnCompleted();
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
            play.setImageResource(R.drawable.ic_baseline_pause_24);
//            mediaService.OnCompleted();
        }
    }

    public void playClick() {
        if (mediaService.isPlaying()) {
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mediaService.pause();
            seekBar.setMax(mediaService.getDuration());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);
                }
            });
            mediaService.showNotification(R.drawable.ic_baseline_play_arrow_24);
        } else {
            play.setImageResource(R.drawable.ic_baseline_pause_24);
            seekBar.setMax(mediaService.getDuration());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaService != null) {
                        int mCurrentPosition = mediaService.getCurrentPosition();
                        seekBar.setProgress(mCurrentPosition);
                        curTime.setText(createTime(mCurrentPosition));
                    }
                    handler.postDelayed(this, 1000);

                }
            });
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
            mediaService.start();
        }
    }

    private ArrayList random(int size, int min, int max) {
        ArrayList numbers = new ArrayList();
        Random random = new Random();
        while (numbers.size() < size) {
            //Get Random numbers between range
            int randomNumber = random.nextInt((max - min) + 1) + min;
            //Check for duplicate values
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber);
            }
        }
        Log.e("random", String.valueOf(numbers));
        return numbers;
    }

    private void getIntentMethod() {
        ListSongs = mangsong;
        if (mediaService != null) {
            mediaService.stop();
            mediaService.release();
        }
        Intent intent = new Intent(getActivity(), MediaService.class);
        intent.putExtra("servicePosition", currentindex);
        getActivity().startService(intent);
    }

    void initViews(View view) {
        prev = view.findViewById(R.id.prev);
        next = view.findViewById(R.id.next);
        play = view.findViewById(R.id.play);
        curTime = view.findViewById(R.id.curTime);
        ovTime = view.findViewById(R.id.ovTime);
        seekBar = view.findViewById(R.id.seekbartime);
        favorite = view.findViewById(R.id.btnfavorite);
        btnloop = view.findViewById(R.id.btnLoop);
        name = view.findViewById(R.id.name);
        author = view.findViewById(R.id.author);
        shuffle = view.findViewById(R.id.shuffle);
        noti = view.findViewById(R.id.btnMore);
        imageView = view.findViewById(R.id.image);
    }

    public String createTime(int millis) {
        StringBuffer buf = new StringBuffer();
//        int hours = (int) (millis / (1000 * 60 * 60));
        int minutes = (int) ((millis % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) (((millis % (1000 * 60 * 60)) % (1000 * 60)) / 1000);

        buf
                .append(String.format("%2d", minutes))
                .append(":")
                .append(String.format("%02d", seconds));

        return buf.toString();
    }


    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MediaService.myBinder binder = (MediaService.myBinder) iBinder;
        mediaService = binder.getService();
//        mediaService.setCallback((ActionPlaying) this);
        seekBar.setMax(mediaService.getDuration());
        ovTime.setText(createTime(mediaService.getDuration()));
        name.setText(ListSongs.get(currentindex).getTitleSong());
//        author.setText(ListSongs.get(currentindex).getIdArtist());
        Glide.with(this).load(ListSongs.get(currentindex).getLinkImage()).into(imageView);
//        mediaService.OnCompleted();
//        mediaService.OnPrepared();
//        mediaService.showNotification(R.drawable.ic_baseline_pause_24);
        Log.e("a", "a");
    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mediaService = null;
    }
}