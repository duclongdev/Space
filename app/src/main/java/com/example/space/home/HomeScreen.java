package com.example.space.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.space.R;
import com.example.space.databinding.FragmentHomeScreenBinding;
import com.example.space.home.advSlide.AdvViewPageAdapter;
import com.example.space.home.advSlide.Advertisement;
import com.example.space.home.advSlide.ZoomOutPageTransformer;
import com.example.space.home.playLists.category.CategoryPlaylist;
import com.example.space.home.playLists.category.CategoryPlaylistAdapter;
import com.example.space.home.playLists.playlist.PlayList;
import com.example.space.myInterface.IClickAdvSlideShow;

import java.util.ArrayList;
import java.util.List;


public class HomeScreen extends Fragment {

    private FragmentHomeScreenBinding binding;
    private List<Advertisement> advList;
    private CategoryPlaylistAdapter categoryPlaylistAdapter;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(binding.advertiseSlide.getCurrentItem() ==  advList.size() - 1){
                binding.advertiseSlide.setCurrentItem(0);
            }
            else {
                binding.advertiseSlide.setCurrentItem(binding.advertiseSlide.getCurrentItem() + 1);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       binding =  FragmentHomeScreenBinding.inflate(inflater, container, false);
       return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setSlideShow();
        getCategoryPlaylits();
    }

    private void getCategoryPlaylits() {
        categoryPlaylistAdapter = new CategoryPlaylistAdapter(HomeScreen.this);
        categoryPlaylistAdapter.setData(getCategoryList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false);
        binding.AllPlayList.setLayoutManager(linearLayoutManager);
        binding.AllPlayList.setAdapter(categoryPlaylistAdapter);
    }

    private List<CategoryPlaylist> getCategoryList() {
        List<CategoryPlaylist> categoryList = new ArrayList<>();
        List<PlayList> list = new ArrayList<>();
        list.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi"));
        list.add(new PlayList(2, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi"));
        list.add(new PlayList(3, "https://images.pexels.com/photos/1408221/pexels-photo-1408221.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihih"));
        list.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","cc"));
        list.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","HIHI"));
        list.add(new PlayList(7,  "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH"));
        list.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA"));
        list.add(new PlayList(1, "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "hihi"));
        list.add(new PlayList(4, "https://images.pexels.com/photos/2387873/pexels-photo-2387873.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","cc"));
        list.add(new PlayList(5, "https://images.pexels.com/photos/1114690/pexels-photo-1114690.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1","HIHI"));
        list.add(new PlayList(7,  "https://images.pexels.com/photos/2246476/pexels-photo-2246476.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "HIHIH"));
        list.add(new PlayList(8, "https://images.pexels.com/photos/325185/pexels-photo-325185.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1", "DASDA"));
        categoryList.add(new CategoryPlaylist(1, "Category 1", list));
        categoryList.add(new CategoryPlaylist(2,"Category 2", list));
        categoryList.add(new CategoryPlaylist(3,"Category 3", list));
        categoryList.add(new CategoryPlaylist(4,"Category 4", list));
        categoryList.add(new CategoryPlaylist(5,"Category 5", list));
        return  categoryList;
    }

    private void setSlideShow() {
        advList =getAdvList();
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

    private List<Advertisement> getAdvList() {
        List<Advertisement> AdvList = new ArrayList<>();
        AdvList.add(new Advertisement(1, "https://images.pexels.com/photos/3183132/pexels-photo-3183132.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        AdvList.add(new Advertisement(2, "https://images.pexels.com/photos/3041110/pexels-photo-3041110.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        AdvList.add(new Advertisement(3,"https://images.pexels.com/photos/1424246/pexels-photo-1424246.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        AdvList.add(new Advertisement(3,"https://images.pexels.com/photos/592077/pexels-photo-592077.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"));
        AdvList.add(new Advertisement(3,"https://images.pexels.com/photos/1198802/pexels-photo-1198802.jpeg?auto=compress&cs=tinysrgb&w=600"));
        return AdvList;
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