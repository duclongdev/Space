package com.example.space.User;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.space.MusicPlayer.MusicPlayer;
import com.example.space.R;

public class UserFragment extends Fragment {

    public UserFragment() {
        // Required empty public constructor
    }
    Button btn;
    int position = 2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        btn = view.findViewById(R.id.btnPlay);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new MusicPlayer();
                Bundle bundle = new Bundle();
                bundle.putInt("data", position);
                fragment.setArguments(bundle);
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.user_container, fragment)
                        .commit();
            }
        });
        return view;
    }
}