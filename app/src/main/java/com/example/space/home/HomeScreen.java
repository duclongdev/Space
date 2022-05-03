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
    private CategoryPlaylistAdapter categoryPlaylistAdapter;
    private Dataservice dataservice;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (binding.advertiseSlide.getCurrentItem() == advList.size() - 1) {
                binding.advertiseSlide.setCurrentItem(0);
            } else {
                binding.advertiseSlide.setCurrentItem(binding.advertiseSlide.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeScreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setSlideShow();
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initToolBar();
        getCategoryPlaylist();
        getdata();
    }

    private void getCategoryPlaylist() {
        categoryPlaylistAdapter = new CategoryPlaylistAdapter(HomeScreen.this, new IClickPlayList() {
            @Override
            public void onClickPlayList(PlayList playList) {
                Toast.makeText(requireContext(), playList.getName(), Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putInt("id_playlist", playList.getId());
                bundle.putString("name_playlist", playList.getName());
                bundle.putString("url_playlist", playList.getUrl());
                bundle.putInt("type_playlist", playList.getType());
                NavHostFragment.findNavController(HomeScreen.this).navigate(R.id.action_homeScreen_to_playlistScreen, bundle);
            }
        });
        categoryPlaylistAdapter.setData(getCategoryList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.AllPlayList.setLayoutManager(linearLayoutManager);
        binding.AllPlayList.setAdapter(categoryPlaylistAdapter);
    }

    private void setSlideShow() {
        dataservice= APIService.getService();
        Call<List<Advertisement>> call=dataservice.getBanner();
        call.enqueue(new Callback<List<Advertisement>>() {
            @Override
            public void onResponse(Call<List<Advertisement>> call, Response<List<Advertisement>> response) {
                advList=response.body();
                AdvViewPageAdapter advViewPageAdapter = new AdvViewPageAdapter(advList, HomeScreen.this, new IClickAdvSlideShow() {
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

            @Override
            public void onFailure(Call<List<Advertisement>> call, Throwable t) {

            }
        });

    }

    private void initToolBar() {
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar2);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
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

    private List<CategoryPlaylist> getCategoryList() {
        List<CategoryPlaylist> categoryList = new ArrayList<>();
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
        List<PlayList> list0 = new ArrayList<>();
        list0.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 0));
        list0.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 0));
        list0.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih", 0));
        list0.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 0));
        list0.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 0));
        list0.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 0));
        list0.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 0));
        list0.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi", 0));
        list0.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "cc", 0));
        list0.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHI", 0));
        list0.add(new PlayList(7, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH", 0));
        list0.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA", 0));
        categoryList.add(new CategoryPlaylist(1, "Recent playlist", list0));
        categoryList.add(new CategoryPlaylist(2, "Greeting the new day", list1));
        categoryList.add(new CategoryPlaylist(3, "Today's selection", list1));
        categoryList.add(new CategoryPlaylist(4, "Featured singer", list2));
        categoryList.add(new CategoryPlaylist(5, "New music every day", list1));
        return categoryList;
    }
}