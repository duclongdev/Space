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
import androidx.navigation.fragment.NavHostFragment;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.databinding.FragmentPlaylistScreenBinding;
import com.example.space.detailPlayllist.Song;
import com.example.space.detailPlayllist.SongListAdapter;
import com.example.space.detailPlayllist.bottomSheet.BotSheetSongMoreOption;
import com.example.space.detailPlayllist.bottomSheet.SongOption;
import com.example.space.home.HomeScreen;
import com.example.space.home.playLists.category.CategoryPlaylist;
import com.example.space.home.playLists.category.CategoryPlaylistAdapter;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.example.space.myInterface.IClickOnMoreOptionOfSongItem;
import com.example.space.myInterface.IClickPlayList;
import com.example.space.myInterface.IClickSong;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;


public class PlaylistScreen extends Fragment {


    private FragmentPlaylistScreenBinding binding;
    private boolean isExpanded = true;
    private SongListAdapter songListAdapter;
    private Menu mMenu;
    private CategoryPlaylistAdapter categoryPlaylistAdapter;
    private int idPlaylist, typePlaylist;
    private String urlPlaylist, namePlaylist;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlaylistScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        initValueForThis();
        initToolBar();
        initRcyView();
        initToolBarAnimation();
        getSuggestPlayList();
    }

    private void getSuggestPlayList() {
        categoryPlaylistAdapter = new CategoryPlaylistAdapter(PlaylistScreen.this, new IClickPlayList() {
            @Override
            public void onClickPlayList(PlayList playList) {
                Bundle bundle = new Bundle();
                bundle.putInt("id_playlist", playList.getId());
                bundle.putString("name_playlist", playList.getName());
                bundle.putString("url_playlist", playList.getUrl());
                bundle.putInt("type_playlist", playList.getType());
                NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_self, bundle);
            }
        });
        categoryPlaylistAdapter.setData(getSuggestList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.suggestPlaylist.setLayoutManager(linearLayoutManager);
        binding.suggestPlaylist.setAdapter(categoryPlaylistAdapter);
    }


    private void initToolBarAnimation() {
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
                if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange() - 100) {
                    isExpanded = false;
                } else {
                    isExpanded = true;
                    ((AppCompatActivity) getActivity()).invalidateOptionsMenu();
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
            public void onClickOptionOnSongItem(String url, String name, String author) {
                clickToOpenBotSheetOfSong(url, name, author);
            }
        });
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this.requireContext(), R.anim.layout_animation_loadlist);
        binding.listSong.setLayoutAnimation(layoutAnimationController);
        songListAdapter.setData(getTempList());
        binding.listSong.setAdapter(songListAdapter);
        binding.hihi.setAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.scroll_list));
    }

    private void clickToOpenBotSheetOfSong(String url, String name, String author) {
        List<SongOption> songOptionList = new ArrayList<>();
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "Thêm vào danh sách yêu thích"));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "Thêm vào playlist "));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "Tải về"));
        songOptionList.add(new SongOption(0, R.drawable.icon_home, "Cài làm nhạc chuông"));
        BotSheetSongMoreOption botSheetSongMoreOption = new BotSheetSongMoreOption(PlaylistScreen.this,
                url, name, author, songOptionList, new IClickItemOnSongOption() {
            @Override
            public void onClickItemOption(int id) {
                Toast.makeText(requireContext(), "id: " + id, Toast.LENGTH_SHORT).show();
            }
        });
        botSheetSongMoreOption.show(requireActivity().getSupportFragmentManager(), botSheetSongMoreOption.getTag());
    }

    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                requireActivity().finish();
                return true;
            case R.id.more_option_playlist:
                List<SongOption> songOptionList = new ArrayList<>();
                songOptionList.add(new SongOption(0, R.drawable.icon_search, "Thêm vào danh sách yêu thích"));
                songOptionList.add(new SongOption(0, R.drawable.icon_home, "Thêm vào playlist "));
                songOptionList.add(new SongOption(0, R.drawable.icon_home, "Tải về"));
                songOptionList.add(new SongOption(0, R.drawable.icon_home, "Cài làm nhạc chuông"));
               clickToOpenBotSheetOfSong(urlPlaylist, namePlaylist, "hihi");
                return true;
        }
        if(item.getTitle().equals("Play")){
            Toast.makeText(requireContext(), "hihi", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initValueForThis() {
        idPlaylist = getArguments().getInt("id_playlist");
        typePlaylist = getArguments().getInt("type_playlist");
        namePlaylist = getArguments().getString("name_playlist");
        urlPlaylist = getArguments().getString("url_playlist");

        binding.detailPLTitle.setText(namePlaylist);
        binding.collapsingToolbarLayout.setTitle(namePlaylist);
        Glide.with(PlaylistScreen.this).load(urlPlaylist).centerCrop().into(binding.detailPLImg);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        if (mMenu != null && (!isExpanded || mMenu.size() != 1)) {
            mMenu.add("Play").setIcon(R.drawable.icon_home).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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

        return tempList;
    }

    private List<CategoryPlaylist> getSuggestList() {
        List<CategoryPlaylist> suggestList = new ArrayList<>();
        List<PlayList> list1 = new ArrayList<>();
        list1.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
        list1.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
        list1.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih", 1));
        list1.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 1));
        list1.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 1));
        list1.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 1));
        list1.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 1));
        list1.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
        list1.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 1));
        list1.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 1));
        list1.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 1));
        list1.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 1));
        List<PlayList> list2 = new ArrayList<>();
        list2.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
        list2.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
        list2.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih", 2));
        list2.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 2));
        list2.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 2));
        list2.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 2));
        list2.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 2));
        list2.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
        list2.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 2));
        list2.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 2));
        list2.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 2));
        list2.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 2));
        suggestList.add(new CategoryPlaylist(1, "Suggest playlist", list1));
        suggestList.add(new CategoryPlaylist(2, "Related author", list2));
        return suggestList;
    }


}