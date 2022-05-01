package com.example.space.detailPlayllist.bottomSheet;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.space.R;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BotSheetSongMoreOption extends BottomSheetDialogFragment {

    private List<SongOption> songOptionsLists;
    private IClickItemOnSongOption iClickItemOnSongOption;
    private String url, title, subTitle;
    private ImageView songImg;
    TextView songTitle, songAuthor;
    private Fragment fragment;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_song_option, null);
        bottomSheetDialog.setContentView(view);

        initComponent(view);
        setComponent();

        RecyclerView recyclerView = view.findViewById(R.id.list_song_option);
        LinearLayoutManager linearLayoutManager =   new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SongMoreOptionAdapter songMoreOptionAdapter = new SongMoreOptionAdapter(songOptionsLists, new IClickItemOnSongOption() {
            @Override
            public void onClickItemOption(int id) {
                iClickItemOnSongOption.onClickItemOption(id);
            }
        });
        recyclerView.setAdapter(songMoreOptionAdapter);

        return  bottomSheetDialog;
    }

    private void setComponent() {
        songTitle.setText(title);
        songAuthor.setText(subTitle);
        Glide.with(fragment).load(url).into(songImg);
    }

    private void initComponent(View view) {
        songImg = view.findViewById(R.id.bottom_sheet_img);
        songTitle = view.findViewById(R.id.bottom_sheet_name_song);
        songAuthor = view.findViewById(R.id.bottom_sheet_author_name);
    }

    public BotSheetSongMoreOption(Fragment fragment, String url, String title, String subTitle, List<SongOption> songOptionsLists, IClickItemOnSongOption iClickItemOnSongOption) {
        this.songOptionsLists = songOptionsLists;
        this.iClickItemOnSongOption = iClickItemOnSongOption;
        this.url = url;
        this.title = title;
        this.subTitle = subTitle;
        this.fragment = fragment;
    }
}
