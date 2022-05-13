package com.example.space.detailPlayllist;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


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
import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.MainActivity;
import com.example.space.R;
import com.example.space.databinding.FragmentPlaylistScreenBinding;
import com.example.space.model.Song;
import com.example.space.detailPlayllist.bottomSheet.BotSheetSongMoreOption;
import com.example.space.detailPlayllist.bottomSheet.SongOption;
import com.example.space.home.playLists.category.CategoryPlaylist;
import com.example.space.home.playLists.category.CategoryPlaylistAdapter;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.example.space.myInterface.IClickOnMoreOptionOfSongItem;
import com.example.space.myInterface.IClickPlayList;
import com.example.space.myInterface.IClickSong;
import com.example.space.search.searchDetail;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlaylistScreen extends Fragment {


    private FragmentPlaylistScreenBinding binding;
    private boolean isExpanded = true;
    private SongListAdapter songListAdapter;
    private Menu mMenu;
    private CategoryPlaylistAdapter categoryPlaylistAdapter;
    private int idPlaylist, typePlaylist;
    private String urlPlaylist, namePlaylist;
//    private ProgressDialog progressDialog;
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
                NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
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
//        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
//        progressDialog.setTitle("Loading");
//        progressDialog.setMessage("Waiting for loading...");
//        progressDialog.show();
        songListAdapter = new SongListAdapter(this, new IClickSong() {
            @Override
            public void onCLickSong(Song song) {
//                progressDialog.show();
                Dataservice dataservice = APIService.getService();
                if (typePlaylist == 1) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongGenre(song.getIdGenre());
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            MainActivity.mangsong.remove(song);
                            MainActivity.mangsong.add(song);
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", MainActivity.mangsong.size() - 1);
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                } else if (typePlaylist == 2) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongArtist(song.getIdArtist());
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            MainActivity.mangsong.remove(song);
                            MainActivity.mangsong.add(song);
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", MainActivity.mangsong.size() - 1);
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                } else if (typePlaylist == 3) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongTheme(song.getIdTheme());
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            MainActivity.mangsong.remove(song);
                            MainActivity.mangsong.add(song);
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", MainActivity.mangsong.size() - 1);
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                }

            }
        }, new IClickOnMoreOptionOfSongItem() {
            @Override
            public void onClickOptionOnSongItem(String url, String name, String author) {
                clickToOpenBotSheetOfSong(url, name, author);
            }
        });
        LayoutAnimationController layoutAnimationController = AnimationUtils.loadLayoutAnimation(this.requireContext(), R.anim.layout_animation_loadlist);
        binding.listSong.setLayoutAnimation(layoutAnimationController);
        Dataservice dataservice = APIService.getService();
        if (typePlaylist == 1) {
            Call<List<Song>> call = dataservice.getSongGenre(String.valueOf(idPlaylist));
            call.enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    List<Song> songs = response.body();
                    songListAdapter.setData(songs);
                    binding.listSong.setAdapter(songListAdapter);
//                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        } else if (typePlaylist == 2) {
            Call<List<Song>> call = dataservice.getSongArtist(String.valueOf(idPlaylist));
            call.enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    List<Song> songs = response.body();
                    songListAdapter.setData(songs);
                    binding.listSong.setAdapter(songListAdapter);
//                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        } else if (typePlaylist == 3) {
            Call<List<Song>> call = dataservice.getSongTheme(String.valueOf(idPlaylist));
            call.enqueue(new Callback<List<Song>>() {
                @Override
                public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                    List<Song> songs = response.body();
                    songListAdapter.setData(songs);
                    binding.listSong.setAdapter(songListAdapter);
//                    progressDialog.hide();
                }

                @Override
                public void onFailure(Call<List<Song>> call, Throwable t) {

                }
            });
        }
        binding.hihi.setAnimation(AnimationUtils.loadAnimation(this.requireContext(), R.anim.scroll_list));
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dataservice dataservice = APIService.getService();
                if (typePlaylist == 1) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongGenre(String.valueOf(idPlaylist));
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", new Random().nextInt(MainActivity.mangsong.size() - 1));
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                } else if (typePlaylist == 2) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongArtist(String.valueOf(idPlaylist));
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", new Random().nextInt(MainActivity.mangsong.size() - 1));
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                } else if (typePlaylist == 3) {
                    MainActivity.mangsong.clear();
                    Call<List<Song>> callSong1 = dataservice.getSongTheme(String.valueOf(idPlaylist));
                    callSong1.enqueue(new Callback<List<Song>>() {
                        @Override
                        public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                            MainActivity.mangsong.addAll(response.body());
                            Bundle bundle = new Bundle();
                            bundle.putInt("data", new Random().nextInt(MainActivity.mangsong.size() - 1));
//                            progressDialog.hide();
                            NavHostFragment.findNavController(PlaylistScreen.this).navigate(R.id.action_playlistScreen_to_musicPlayer2, bundle);
                        }

                        @Override
                        public void onFailure(Call<List<Song>> call, Throwable t) {

                        }
                    });
                }


            }
        });
    };


    private void clickToOpenBotSheetOfSong(String url, String name, String author) {
        List<SongOption> songOptionList = new ArrayList<>();
        songOptionList.add(new SongOption(0, R.drawable.icon_heart, "Thêm vào danh sách yêu thích"));
        songOptionList.add(new SongOption(0, R.drawable.ic_baseline_playlist_add_24, "Thêm vào playlist "));
        songOptionList.add(new SongOption(0, R.drawable.ic_baseline_cloud_download_24, "Tải về"));
        songOptionList.add(new SongOption(0, R.drawable.ic_baseline_notifications_active_24, "Cài làm nhạc chuông"));
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
                songOptionList.add(new SongOption(0, R.drawable.icon_heart, "Thêm vào danh sách yêu thích"));
                songOptionList.add(new SongOption(0, R.drawable.ic_baseline_playlist_add_24, "Thêm vào playlist "));
                songOptionList.add(new SongOption(0, R.drawable.ic_baseline_cloud_download_24, "Tải về"));
                songOptionList.add(new SongOption(0, R.drawable.ic_baseline_notifications_active_24, "Cài làm nhạc chuông"));
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
            mMenu.add("Play").setIcon(R.drawable.ic_baseline_play_arrow_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
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


    private List<CategoryPlaylist> getSuggestList() {
        List<CategoryPlaylist> suggestList = new ArrayList<>();
//        List<PlayList> list1 = new ArrayList<>();
//        list1.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
//        list1.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
//        list1.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih", 1));
//        list1.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 1));
//        list1.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 1));
//        list1.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 1));
//        list1.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 1));
//        list1.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 1));
//        list1.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 1));
//        list1.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 1));
//        list1.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 1));
//        list1.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 1));
//        List<PlayList> list2 = new ArrayList<>();
//        list2.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
//        list2.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
//        list2.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih", 2));
//        list2.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 2));
//        list2.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 2));
//        list2.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 2));
//        list2.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 2));
//        list2.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 2));
//        list2.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 2));
//        list2.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 2));
//        list2.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 2));
//        list2.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 2));
//        suggestList.add(new CategoryPlaylist(1, "Suggest playlist", list1));
//        suggestList.add(new CategoryPlaylist(2, "Related author", list2));
        return suggestList;
    }


}