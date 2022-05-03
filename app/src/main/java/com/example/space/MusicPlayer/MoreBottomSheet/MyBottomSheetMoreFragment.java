package com.example.space.MusicPlayer.MoreBottomSheet;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.space.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class MyBottomSheetMoreFragment extends BottomSheetDialogFragment {
    private List<More_Item> list;
    private IClickItemMoreListener iClickItemMoreListener;
    private ImageView imageView;
    private TextView tvTitle, tvArtist;
    Drawable image;
    String title, artist;
    public MyBottomSheetMoreFragment(List<More_Item> list, IClickItemMoreListener iClickItemMoreListener, Drawable image, String title, String artist) {
        this.list = list;
        this.iClickItemMoreListener = iClickItemMoreListener;
        this.image= image;
        this.title = title;
        this.artist = artist;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_more_bottomsheet, null);
        tvTitle = view.findViewById(R.id.titleMore);
        tvArtist = view.findViewById(R.id.artistMore);
        imageView = view.findViewById(R.id.imageMore);
        imageView.setImageDrawable(image);
        tvTitle.setText(title);
        tvArtist.setText(artist);
        bottomSheetDialog.setContentView(view);
        RecyclerView rcv = view.findViewById(R.id.rcvMore);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linearLayoutManager);
        More_Item_Adapter item_adapter = new More_Item_Adapter(list, new IClickItemMoreListener() {
            @Override
            public void Clickitem(More_Item item_object) {
                iClickItemMoreListener.Clickitem(item_object);
            }
        });
//        setupFullHeight(bottomSheetDialog);
        rcv.setAdapter(item_adapter);
        return bottomSheetDialog;
    }
    private void setupFullHeight(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();

        int windowHeight = getWindowHeight();
        if (layoutParams != null) {
            layoutParams.height = windowHeight;
        }
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getWindowHeight() {
        // Calculate window height for fullscreen use
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}
