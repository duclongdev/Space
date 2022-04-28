package com.example.space.detailPlayllist;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.space.R;
import com.example.space.databinding.FragmentPlaylistScreenBinding;
import com.example.space.detailPlayllist.Song;
import com.example.space.detailPlayllist.SongListAdapter;
import com.example.space.detailPlayllist.bottomSheet.BotSheetSongMoreOption;
import com.example.space.detailPlayllist.bottomSheet.SongOption;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.example.space.myInterface.IClickOnMoreOptionOfSongItem;
import com.example.space.myInterface.IClickSong;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;


public class PlaylistScreen extends Fragment {


    private FragmentPlaylistScreenBinding binding;
    private boolean isExpanded = true;
    private SongListAdapter songListAdapter;
    private Menu mMenu;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initToolBar();
        initRcyView();
        initToolBarAnimation();
    }

    private void initToolBarAnimation() {
        binding.collapsingToolbarLayout.setTitle("Sơn tùng tmp");

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mtp);
//        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(@Nullable Palette palette) {
//                int myColor = palette.getVibrantColor(getResources().getColor(R.color.background));
//                binding.collapsingToolbarLayout.setContentScrimColor(myColor);
//                binding.collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.teal_700));
//            }
//        });
        binding.appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(Math.abs(verticalOffset) > 450) {
                    isExpanded = false;
                   // binding.collapsingToolbarLayout.setTitle("Sơn tùng tmp");
                }
                else {
                    isExpanded = true;
                    ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
                   // binding.collapsingToolbarLayout.setTitle("");
                }
            }
        });
    }
    private void initRcyView() {
        binding.listSong.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.requireContext());
        binding.listSong.setLayoutManager(linearLayoutManager);

        songListAdapter = new SongListAdapter(this, new IClickSong() {
            @Override
            public void onCLickSong(int id) {
                Toast.makeText(requireContext(), "id: " + id, Toast.LENGTH_SHORT).show();
            }
        }, new IClickOnMoreOptionOfSongItem() {
            @Override
            public void onClickOptionOnSongItem(int id) {
                clickToOpenBotSheetOfSong();
            }
        });
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this.requireContext(), R.anim.layout_animation_loadlist);
        binding.listSong.setLayoutAnimation(layoutAnimationController);
        songListAdapter.setData(getTempList());
        binding.listSong.setAdapter(songListAdapter);


    }

    private void clickToOpenBotSheetOfSong() {
        List<SongOption> songOptionList = new ArrayList<>();
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "hihi"));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "hihi"));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "hihi"));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "hihi"));
        BotSheetSongMoreOption botSheetSongMoreOption = new BotSheetSongMoreOption(songOptionList, new IClickItemOnSongOption() {
            @Override
            public void onClickItemOption(int id) {
                Toast.makeText(requireContext(), "id: " + id, Toast.LENGTH_SHORT).show();
            }
        });
        botSheetSongMoreOption.show(requireActivity().getSupportFragmentManager(), botSheetSongMoreOption.getTag());
    }

    private List<Song> getTempList() {
        List<Song> tempList = new ArrayList<>();
        tempList.add(new Song(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(2, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(3, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(4, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(5, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(6, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(8, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(9, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(10, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(11, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(12, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(13, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(14, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(15, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hiih"));
        tempList.add(new Song(16, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));
        tempList.add(new Song(17, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "emcuar ngay hom qua", "hihi"));

        return tempList;
    }

    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {

        if(mMenu != null  && (!isExpanded || mMenu.size() != 1)){
            mMenu.add("Add").setIcon(R.drawable.icon_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{

        }
        super.onPrepareOptionsMenu(mMenu);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.playlist_menu, menu);
        mMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

}