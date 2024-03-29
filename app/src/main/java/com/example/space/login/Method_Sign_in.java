package com.example.space.login;

import static com.example.space.User.UserFragment.user1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.space.Home;
import com.example.space.R;
import com.example.space.register.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class Method_Sign_in extends Fragment {
    private static final String EMAIL = "email";
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog progressDialog;
    private TextView btn_SI;
    private LinearLayout btn_SU;
    private LinearLayout btn_gg;
    private LinearLayout btn_fb;
    private boolean isFb;
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_method__sign_in, container, false);
        Init(v);

        return v;
    }
    private void Init(View v){
        btn_SI=v.findViewById(R.id.btn_SI);
        btn_SU=v.findViewById(R.id.btn_SU);
        btn_gg=v.findViewById(R.id.btn_gg);
        btn_fb=v.findViewById(R.id.btn_fb);
        mAuth = FirebaseAuth.getInstance();
        //sự kiện nút đăng ký
        isFb=true;
        btn_SI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Method_Sign_in.this).navigate(R.id.action_method_Sign_in_to_sign_in);
            }
        });
        //sự kiện nút đăng nhập bằng tài khoản account
        btn_SU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Method_Sign_in.this).navigate(R.id.action_method_Sign_in_to_enterEmail);
            }
        });
        //đăng nhập bằng facebook
        LoginButton loginButton = v.findViewById(R.id.login_button);
        FacebookSdk.sdkInitialize(requireActivity());
        loginButton.setReadPermissions("email", "public_profile");
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {



            }

            @Override
            public void onError(@NonNull FacebookException e) {


            }
        });
//        mCallbackManager = CallbackManager.Factory.create();
        btn_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginButton.performClick();
            }
        });
        btn_gg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                    }
                });
        //đăng nhập bằng google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);
    }
    private void handleFacebookAccessToken(@NonNull AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            isFb=true;
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkExist(user);
                            updateUI(user);
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null&&currentUser.isEmailVerified())
        {
            updateUI(currentUser);
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        progressDialog=new ProgressDialog(requireActivity(),R.style.MyAlertDialogStyle);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Waiting for loading...");
        progressDialog.show();
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkExist(user);
                            updateUI(user);
                            progressDialog.hide();
                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    private void checkExist(FirebaseUser user) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.hasChild(mAuth.getUid())){
                    User users=new User(mAuth.getCurrentUser().getDisplayName(),"",mAuth.getCurrentUser().getEmail());
                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getUid()).setValue(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    //cập nhật lại giao diện
    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null&&(currentUser.isEmailVerified()||isFb)){
            Intent intent=new Intent(requireActivity(),Home.class);
            startActivity(intent);
        }
        else {
            return;
        }
    }
}