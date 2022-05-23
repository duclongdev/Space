package com.example.space.MusicPlayer;

import static android.content.Context.BIND_AUTO_CREATE;

import static com.example.space.MainActivity.mangsong;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.palette.graphics.Palette;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.space.API.APIRetrofitClient;
import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.MusicPlayer.MoreBottomSheet.IClickItemMoreListener;
import com.example.space.MusicPlayer.MoreBottomSheet.More_Item;
import com.example.space.MusicPlayer.MoreBottomSheet.MyBottomSheetMoreFragment;
import com.example.space.R;
import com.example.space.Service.MediaService;
import com.example.space.detailPlayllist.PlaylistScreen;
import com.example.space.model.Song;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MusicPlayer extends Fragment implements ActionPlaying, ServiceConnection {

    ImageButton prev, next, btnloop, shuffle;
    TextView curTime, ovTime, name, author;
    FloatingActionButton play, more, back;
    SeekBar seekBar;
    ImageView favorite, imageView;
    LinearLayout layout;
    private Runnable runnable;
    private MediaController mediaController;
    private AudioManager audioManager;
    private Handler handler = new Handler();
    int currentindex = 0, position;
    static ArrayList<Song> ListSongs = new ArrayList<>();
    private Thread playThread, prevThread, nextThread;
    MediaService mediaService;
    ArrayList<Integer> listPlay, listHide = new ArrayList<>();
    boolean isloop = false;
    boolean isStop = false;
    List<More_Item> listBottomSheet;
    private CountDownTimer countDownTimer;
    MyBottomSheetMoreFragment myBottomSheetMoreFragment;
    public MusicPlayer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music_player, container, false);
        initViews(view);
        SetDataBottomSheet();
        currentindex = getArguments().getInt("data"); // đây là thứ bạn cần :D
        getIntentMethod();
        position = listPlay.get(currentindex);
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
                if (!mediaService.isLooping()) {
                    btnloop.setImageResource(R.drawable.ic_baseline_repeat_black_24);
                    mediaService.setLooping(true);
                    isloop = true;
                } else {
                    btnloop.setImageResource(R.drawable.ic_baseline_repeat_24);
                    mediaService.setLooping(false);
                    isloop = false;
                }

            }
        });
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (shuffle.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_baseline_shuffle_24).getConstantState()) {
                    shuffle.setImageResource(R.drawable.ic_baseline_shuffle_black_24);
                    listPlay = random(ListSongs.size(), 0, ListSongs.size() - 1);
                    currentindex = listPlay.indexOf(position);
                } else {
                    currentindex = position;
                    shuffle.setImageResource(R.drawable.ic_baseline_shuffle_24);
                    listPlay.clear();
                    for (int i = 0; i < ListSongs.size(); i++) {
                        listPlay.add(i);
                    }
                }
            }
        });
        Dataservice dataservice= APIService.getService();
        Call<String> check=dataservice.checkFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid(),ListSongs.get(position).getIdSong());
        check.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.body().equals("co")){
                    favorite.setImageResource(R.drawable.ic_heart_red);
                }else{
                    favorite.setImageResource(R.drawable.ic_heart_outline);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favorite.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.ic_heart_outline).getConstantState())
                {
                    String idFavorite=FirebaseAuth.getInstance().getCurrentUser().getUid()+ListSongs.get(position).getIdSong();
                    Dataservice dataservice= APIService.getService();
                    Call<String> check=dataservice.addFavorite(idFavorite,FirebaseAuth.getInstance().getCurrentUser().getUid(),ListSongs.get(position).getIdSong());
                    check.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("thanhcong")){
                                favorite.setImageResource(R.drawable.ic_heart_red);
                            }else{
                                favorite.setImageResource(R.drawable.ic_heart_outline);
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
                else {
                    Dataservice dataservice= APIService.getService();
                    Call<String> check=dataservice.removeFavorite(FirebaseAuth.getInstance().getCurrentUser().getUid(),ListSongs.get(position).getIdSong());
                    check.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.body().equals("thanhcong")){
                                favorite.setImageResource(R.drawable.ic_heart_outline);
                            }else{
                                favorite.setImageResource(R.drawable.ic_heart_red);
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myBottomSheetMoreFragment = new MyBottomSheetMoreFragment(listBottomSheet, new IClickItemMoreListener() {
                    @Override
                    public void Clickitem(More_Item item_object) {
                        SolveBottomSheet(item_object);
                    }
                }, imageView.getDrawable(), name.getText().toString(), author.getText().toString());
                myBottomSheetMoreFragment.show(getActivity().getSupportFragmentManager(), myBottomSheetMoreFragment.getTag());
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(MusicPlayer.this).popBackStack();
            }
        });
        return view;
    }

    private void SetDataBottomSheet() {
        listBottomSheet = new ArrayList<>();
        listBottomSheet.add(new More_Item("Like", R.drawable.ic_heart_outline));
        listBottomSheet.add(new More_Item("Hide", R.drawable.ic_remove_circle_outline));
        listBottomSheet.add(new More_Item("Sleep time", R.drawable.ic_moon_outline));
    }

    private void SolveBottomSheet(More_Item item_object) {
        switch (item_object.getTitle()) {
            case "Like":
//                item_object.setTitle("Nguyen");
                Toast.makeText(getContext(), item_object.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case "Hide":
//                HideSong();
                Toast.makeText(getContext(), item_object.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case "Sleep time":

                Toast.makeText(getContext(), item_object.getTitle(), Toast.LENGTH_SHORT).show();
                myBottomSheetMoreFragment.dismiss();
                OpenTimePicker(item_object);
                break;
        }
    }

    private void HideSong() {
        listHide.add(position);
        Log.e("position", String.valueOf(listHide.get(0)));
        Log.e("position", String.valueOf(currentindex));
        Log.e("position", String.valueOf(position));
        nextClick();
    }

    private void OpenTimePicker(More_Item item) {
        View view = getLayoutInflater().inflate(R.layout.time_sleep_picker, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        LinearLayout btn5, btn10, btn15, btn30, btn1h, btnEOT;
        btn5 = view.findViewById(R.id.m5);
        btn10 = view.findViewById(R.id.m10);
        btn15 = view.findViewById(R.id.m15);
        btn30 = view.findViewById(R.id.m30);
        btn1h = view.findViewById(R.id.h1);
        btnEOT = view.findViewById(R.id.endOfTrack);
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("5", bottomSheetDialog, item);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("10", bottomSheetDialog, item);
            }
        });
        btn15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("15", bottomSheetDialog, item);
            }
        });
        btn30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("30", bottomSheetDialog, item);
            }
        });
        btn1h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("1", bottomSheetDialog, item);
            }
        });
        btnEOT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerClick("End", bottomSheetDialog, item);
            }
        });

    }

    private void TimePickerClick(String time, BottomSheetDialog bottomSheetDialog, More_Item item) {
        int time1;
        if(time != "End")
            time1 = Integer.parseInt(time);
        else time1 = 0;

        SetSleep(time1*60000);
//        switch (time) {
//            case "5":
//                SetSleep(10000);
//                break;
//            case "10":
//                SetSleep(600000);
//                break;
//            case "15":
//                SetSleep(900000);
//                break;
//            case "30":
//                SetSleep(1800000);
//                break;
//            case "1":
//                SetSleep(3600000);
//                break;
//            case "End":
//                SetSleep(0);
//                break;
//        }
        item.setImage(R.drawable.ic_moon);
        Toast.makeText(getContext(), "Your sleep timer is set", Toast.LENGTH_SHORT);
        bottomSheetDialog.dismiss();
    }

    private void SetSleep(long time) {
        long time1;
        if (time != 0)
            time1 = time;
        else
            time1 = mediaService.getDuration() - mediaService.getCurrentPosition();
        if (countDownTimer != null)
            countDownTimer.cancel();
        countDownTimer = new CountDownTimer(time1, 1000) {
            @Override
            public void onTick(long l) {
                Log.e("timer", String.valueOf(l));
            }

            @Override
            public void onFinish() {
                isStop = true;
                playClick();
            }
        };
        countDownTimer.start();
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

    public void playClick() {
        boolean is = false;
        if (isStop == true && (mediaService.getCurrentPosition() < mediaService.getDuration() + 1000 || mediaService.getCurrentPosition() < 2000)) {
            if (mediaService.getCurrentPosition() < 1000)
                currentindex--;
            is = true;
            if (!isloop)
                nextClick();
        }
        if (mediaService.isPlaying() || is) {
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            mediaService.showNotification(R.drawable.ic_baseline_play_arrow_24);
            mediaService.pause();
            seekBar.setMax(mediaService.getDuration());
            setImage_showNotification(true);
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
        } else {
            play.setImageResource(R.drawable.ic_baseline_pause_24);
            seekBar.setMax(mediaService.getDuration());
            setImage_showNotification(false);
            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
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

            mediaService.start();
        }
        isStop = false;
    }

    public void prevClick() {
        reduceCurrentIndex();
        UpdatePlay(false);
    }

    @Override
    public void nextClick() {
        increaseCurrentIndex();
        UpdatePlay(true);
    }

    private void getPosition(boolean isNext) {
        position = listPlay.get(currentindex);
        while (isHide()) {
            if (isNext) {
                increaseCurrentIndex();
            } else {
                reduceCurrentIndex();
            }
            position = listPlay.get(currentindex);
        }
        position = listPlay.get(currentindex);
    }

    private boolean isHide() {
        if (listHide.contains(position))
            return true;
        return false;
    }

    private void reduceCurrentIndex() {
        if (currentindex > 0)
            currentindex--;
        else {
            currentindex = ListSongs.size() - 1;
        }
    }

    private void increaseCurrentIndex() {
        if (currentindex < ListSongs.size() - 1)
            currentindex++;
        else {
            currentindex = 0;
        }
    }

    private void UpdatePlay(boolean isNext) {
        getPosition(isNext);
        seekBar.setProgress(0);
        mediaService.stop();
        mediaService.release();
        if (!isloop)
            mediaService.createMediaPlayer(position, false, isStop);
        else {
            mediaService.createMediaPlayer(position, true, isStop);
        }
        seekBar.setMax(mediaService.getDuration());
        name.setText(ListSongs.get(position).getTitleSong());
        ovTime.setText(createTime(mediaService.getDuration()));
        play.setImageResource(R.drawable.ic_baseline_pause_24);
        author.setText(ListSongs.get(position).getName());
        setImage_showNotification(false);
        mediaService.showNotification(R.drawable.ic_baseline_pause_24);
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
        imageView = view.findViewById(R.id.image);
        more = view.findViewById(R.id.btnMore);
        layout = view.findViewById(R.id.linearlayout);
        back=view.findViewById(R.id.btn_mp_back);
    }

    private void getIntentMethod() {
        ListSongs = mangsong;
        listPlay = new ArrayList<>();
        for (int i = 0; i < ListSongs.size(); i++) {
            listPlay.add(i);
        }
        position = listPlay.get(currentindex);
        if (mediaService != null) {
            mediaService.stop();
            mediaService.release();
        }
        Intent intent = new Intent(getActivity(), MediaService.class);
        intent.putExtra("servicePosition", position);
        getActivity().startService(intent);
    }

    public void createPaletteSync(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch swatch = palette.getDominantSwatch();
                Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
                if (swatch != null) {
                    GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{swatch.getRgb(), darkMutedSwatch.getRgb(), darkMutedSwatch.getRgb()});
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

    private void paletteGenerator(BitmapDrawable drawable) {
        Bitmap bitmap = drawable.getBitmap();
        createPaletteSync(bitmap);
//        Palette palette = createPaletteSync(bitmap);
//        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
//        Palette.Swatch darkMutedSwatch = palette.getDarkMutedSwatch();
//        if(vibrantSwatch != null)
//        {
//            int bgColor = vibrantSwatch.getRgb();
//            layout.setBackgroundColor(bgColor);
//            getWindow().setStatusBarColor(bgColor);
//        }else if(darkMutedSwatch != null){
//            int bgColor = vibrantSwatch.getRgb();
//            layout.setBackgroundColor(bgColor);
//        }else{
//            layout.setBackgroundColor(Color.WHITE);
//        }
    }

    void setImage_showNotification(boolean isPlaying) {
        Glide.with(this)
                .asBitmap()
                .load(ListSongs.get(position).getLinkImageS())
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        imageView.setImageBitmap(resource);
                        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
                        if (!isPlaying)
                            mediaService.showNotification(R.drawable.ic_baseline_pause_24);
                        else
                            mediaService.showNotification(R.drawable.ic_baseline_play_arrow_24);
                        paletteGenerator(drawable);
//                                isStop = false;
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        MediaService.myBinder binder = (MediaService.myBinder) iBinder;
        mediaService = binder.getService();
        mediaService.setCallback((ActionPlaying) this);
        seekBar.setMax(mediaService.getDuration());
        ovTime.setText(createTime(mediaService.getDuration()));
        name.setText(ListSongs.get(position).getTitleSong());
        author.setText(ListSongs.get(position).getName());
        setImage_showNotification(false);
        mediaService.showNotification(R.drawable.ic_baseline_pause_24);

    }

    @Override
    public void onServiceDisconnected(ComponentName componentName) {
        mediaService = null;
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
}