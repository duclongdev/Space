package com.example.space.User;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.space.MainActivity;
import com.example.space.R;
import com.example.space.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends Fragment {
    FirebaseUser user;
    EditText tvName, tvEmail, tvBirth;
    LinearLayout layoutName, layoutEmail, layoutDateOfbirth;
    ImageButton calendar;
    Button btnEdit, btnSave;
    DatabaseReference mDatabase;
    User user1;
    String name = "";
    String email = "";
    Uri photoUrl;
    DatePickerDialog datePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("user", user.getPhotoUrl().toString());
        getData(user.getUid());
        layoutName = view.findViewById(R.id.include1);
        layoutEmail = view.findViewById(R.id.include2);
        layoutDateOfbirth = view.findViewById(R.id.include3);
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        btnEdit = view.findViewById(R.id.Edit);
        btnSave = view.findViewById(R.id.Save);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (user != null) {
            // Name, email address, and profile photo Url

            Log.e("uid", user.getUid());
            //get data
//            photoUrl = user.getPhotoUrl();
            //
            TextView tvNametype, tvEmailType, tvBirthType;
            ImageView icEmail, icBirth;
            //initview
            tvName = layoutName.findViewById(R.id.content);
            tvEmail = layoutEmail.findViewById(R.id.content);
            tvEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
            tvBirth = layoutDateOfbirth.findViewById(R.id.content);
            calendar = layoutDateOfbirth.findViewById(R.id.calendar);
            calendar.setVisibility(View.VISIBLE);

            tvBirth.setEnabled(false);
            tvNametype = layoutName.findViewById(R.id.type);
            tvEmailType = layoutEmail.findViewById(R.id.type);
            tvBirthType = layoutDateOfbirth.findViewById(R.id.type);
            icEmail = layoutEmail.findViewById(R.id.icon);
            icBirth = layoutDateOfbirth.findViewById(R.id.icon);
            //setContent
            tvNametype.setText("Name");
            tvEmailType.setText("Email");
            tvBirthType.setText("Date of birth");
//            Log.e("user", user1.getAge());
            icEmail.setImageResource(R.drawable.ic_baseline_email_24);
            icBirth.setImageResource(R.drawable.ic_baseline_calendar_today_24);
//            if(user.getPhotoUrl().equals(Uri.EMPTY))
//                Log.e("Photo", "sss");

//            setImage(photoUrl.toString());
//            this.tvname.setText(tvname);

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tvBirth.setEnabled(false);
//                calendar.setEnabled(false);
//                tvEmail.setEnabled(false);
//                tvName.setEnabled(false);
                user1.setName(tvName.getText().toString());
                user1.setAge(tvBirth.getText().toString());
                user1.setEmail(tvEmail.getText().toString());
                mDatabase.child("i1X6T6cR8hWHZXClSi3wrVYWv2t2").updateChildren(user1.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(), "Updated", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                    tvBirth.setEnabled(true);
//                    tvBirth.setFocusable(false);
//                    tvBirth.setInputType(InputType.TYPE_NULL);
//                    tvBirth.setClickable(true);
//                calendar.setEnabled(true);
//                tvEmail.setEnabled(true);
//                tvName.setEnabled(true);
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.editProfile,SignOut.class,null)
                        .commit();
            }

        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String birth = tvBirth.getText().toString();
                String[] arrOfStr = birth.split("/", -2);
                int mYear = Integer.valueOf(arrOfStr[2]);// current year
                int mMonth = Integer.valueOf(arrOfStr[1]) - 1; // current month
                int mDay = Integer.valueOf(arrOfStr[0]); // current day
                datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                tvBirth.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);
                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        return view;
    }


    private void getData(String uid) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child("i1X6T6cR8hWHZXClSi3wrVYWv2t2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user1 = snapshot.getValue(User.class);
                tvName.setText(user1.getName());
                tvEmail.setText(user1.getEmail());
                tvBirth.setText(user1.getAge());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}