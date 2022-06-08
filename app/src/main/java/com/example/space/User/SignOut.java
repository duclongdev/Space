package com.example.space.User;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.space.MainActivity;
import com.example.space.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignOut extends Fragment {
    FirebaseUser user;
    LinearLayout version, term_condition, policy, support, logout;
    FirebaseAuth auth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_out, container, false);
        logout = view.findViewById(R.id.logout);
        policy = view.findViewById(R.id.policy);
        support = view.findViewById(R.id.suport);
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto: 20521450@gm.uit.edu.vn")); // only email apps should handle this
                startActivity(intent);
//                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"kuti113ct@gmail.com"});
//                intent.putExtra(Intent.EXTRA_SUBJECT, "khanh");
//                if (intent.resolveActivity(getContext().getPackageManager()) != null) {
//                }
            }
        });
        policy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.spotify.com/vn-vi/legal/privacy-policy/plain/"));
                startActivity(browserIntent);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignOut();
            }
        });
        return view;
    }

    private void SignOut() {
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        Intent main = new Intent(getActivity(), MainActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(main);
        getActivity().finish();
    }
}