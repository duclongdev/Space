package com.example.space.detailPlayllist.bottomSheet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;
import com.example.space.myInterface.IClickItemOnSongOption;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class BotSheetSongMoreOption extends BottomSheetDialogFragment {

    private List<SongOption> songOptionsLists;
    private IClickItemOnSongOption iClickItemOnSongOption;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet_song_option, null);
        bottomSheetDialog.setContentView(view);
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
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(),  DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        return  bottomSheetDialog;
    }

    public BotSheetSongMoreOption(List<SongOption> songOptionsLists, IClickItemOnSongOption iClickItemOnSongOption) {
        this.songOptionsLists = songOptionsLists;
        this.iClickItemOnSongOption = iClickItemOnSongOption;
    }
}
