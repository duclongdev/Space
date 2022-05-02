package com.example.space.home;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.space.API.APIService;
import com.example.space.API.Dataservice;
import com.example.space.R;
import com.example.space.databinding.FragmentHomeScreenBinding;
import com.example.space.home.advSlide.AdvViewPageAdapter;
import com.example.space.home.advSlide.Advertisement;
import com.example.space.home.advSlide.ZoomOutPageTransformer;
import com.example.space.home.playLists.category.CategoryPlaylist;
import com.example.space.home.playLists.category.CategoryPlaylistAdapter;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.model.Artist;
import com.example.space.model.Genre;
import com.example.space.model.Theme;
import com.example.space.myInterface.IClickAdvSlideShow;
import com.example.space.myInterface.IClickPlayList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeScreen extends Fragment {

    private FragmentHomeScreenBinding binding;
    private List<Advertisement> advList;
    private List<Artist> artistList;
    private List<Theme> themeList;
    private CategoryPlaylistAdapter categoryPlaylistAdapter;
    private List<CategoryPlaylist> categoryPlaylists;
    private Dataservice dataservice;
    private AdvViewPageAdapter advViewPageAdapter;
    private Handler handler;
    private Runnable runnable;
    private Bundle passIdPlayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initAllValue();
        initToolBar();
        initSlideShow();
        InitCategoryPlaylist();
        LoadAllDataForCategory();
    }

    private void initAllValue() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (binding.advertiseSlide.getCurrentItem() == advList.size() - 1) {
                    binding.advertiseSlide.setCurrentItem(0);
                } else {
                    binding.advertiseSlide.setCurrentItem(binding.advertiseSlide.getCurrentItem() + 1);
                }
            }
        };
        dataservice = APIService.getService();
        categoryPlaylists = new ArrayList<>();
        advList = new ArrayList<>();
    }


    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar2);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }


    private void initSlideShow() {
        advViewPageAdapter = new AdvViewPageAdapter(advList, HomeScreen.this, new IClickAdvSlideShow() {
            @Override
            public void onClickAdvItem(int id) {
                Toast.makeText(getContext(), "id: " + id, Toast.LENGTH_SHORT).show();
            }
        });
        binding.advertiseSlide.setAdapter(advViewPageAdapter);
        binding.transitionIndicator.setViewPager(binding.advertiseSlide);
        binding.advertiseSlide.setPageTransformer(new ZoomOutPageTransformer());
        binding.advertiseSlide.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }


    private void InitCategoryPlaylist() {
        categoryPlaylistAdapter = new CategoryPlaylistAdapter(HomeScreen.this, new IClickPlayList() {
            @Override
            public void onClickPlayList(PlayList playList) {
                Toast.makeText(requireContext(), playList.getName(), Toast.LENGTH_SHORT).show();
                InitBundle(playList);
                NavHostFragment.findNavController(HomeScreen.this).navigate(R.id.action_homeScreen_to_playlistScreen, passIdPlayList);
            }
        });
        categoryPlaylistAdapter.setData(categoryPlaylists);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.AllPlayList.setLayoutManager(linearLayoutManager);
        binding.AllPlayList.setAdapter(categoryPlaylistAdapter);
    }


    private void LoadAllDataForCategory() {
        loadArtistCategory();
        loadThemeCategory();
        setSlideShow();
    }

    private void setSlideShow() {
        Call<List<Advertisement>> call = dataservice.getBanner();
        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                advList.addAll(response.body());
                advViewPageAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {

            }
        });

    }


    private void loadArtistCategory() {
        artistList = new ArrayList<>();
        List<PlayList> playLists = new ArrayList<>();
        Call<List<Artist>> call = dataservice.getArtist();
        call.enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                artistList.addAll(response.body());
                for (Artist artist : artistList) {
                    playLists.add(new PlayList(Integer.parseInt(artist.getIdArtist()), artist.getLinkImage(), artist.getName(), 2));
                }
                categoryPlaylists.add(new CategoryPlaylist(1, "Artist", playLists));
                categoryPlaylistAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {

            }
        });
    }

    private void loadThemeCategory() {
        themeList = new ArrayList<>();
        List<PlayList> playLists = new ArrayList<>();
        Call<List<Theme>> call = dataservice.getTheme();
        call.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                themeList.addAll(response.body());
                for (Theme theme : themeList) {
                    playLists.add(new PlayList(Integer.parseInt(theme.getIdTheme()), theme.getLinkTheme(), theme.getName(), 1));
                }
                categoryPlaylists.add(new CategoryPlaylist(1, "Playlist for new day", playLists));
                categoryPlaylistAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {

            }
        });
    }
    private void InitBundle(PlayList playList) {
        passIdPlayList = new Bundle();
        passIdPlayList.putInt("id_playlist", playList.getId());
        passIdPlayList.putString("name_playlist", playList.getName());
        passIdPlayList.putString("url_playlist", playList.getUrl());
        passIdPlayList.putInt("type_playlist", playList.getType());
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}