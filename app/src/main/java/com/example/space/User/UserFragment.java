package com.example.space.User;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.space.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.IOException;

public class UserFragment extends Fragment {
    TextView tvname;
    FirebaseAuth auth;
    CardView chooseImage;
    ImageView Avatar;
    Button btnEdit;
    LinearLayout layout;
    public UserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        auth = FirebaseAuth.getInstance();
        tvname = view.findViewById(R.id.username);
        btnEdit = view.findViewById(R.id.btnEdit);
        chooseImage = view.findViewById(R.id.chooseImage);
        Avatar = view.findViewById(R.id.avatar);
        layout = view.findViewById(R.id.layoutUser);
        try {
            setImage(auth.getCurrentUser().getPhotoUrl().toString());
        }catch (Exception e){
            Log.e("Avatar", "No avatar");
        }

        tvname.setText(auth.getCurrentUser().getDisplayName());
        createPaletteSync();
        Avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangeAvatar();
            }
        });
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "title"), 3);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.editProfile,EditProfile.class,null)
                        .commit();
//                Intent intent = new Intent(getContext(), EditProfile.class);
//                startActivity(intent);
            }
        });
        return view;
    }

    private void ChangeAvatar() {

    }
    private void setImage(String src) {
        Glide.with(this)
                .asBitmap()
                .load(src)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Avatar.setImageBitmap(resource);
                    }
                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 3){
            Uri uri = data.getData();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Confirmation").setMessage("Do you want to close this app?");
            builder.setCancelable(true);
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Avatar.setImageURI(uri);
                    setImage(uri);
                }
            });
//            builder.setPositiveButtonIcon();

            // Create "No" button with OnClickListener.
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    private void setImage(Uri uri) {
        FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
        if(user == null)
            return;
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                            Toast.makeText(requireActivity(), "completed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void createPaletteSync(){
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                            new int[]{0xff212121,0xff212121,0xff212121, 0xff5C5845});
        layout.setBackground(gradientDrawable);
    }
}