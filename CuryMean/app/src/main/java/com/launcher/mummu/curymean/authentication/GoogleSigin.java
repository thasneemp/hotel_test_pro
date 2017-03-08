package com.launcher.mummu.curymean.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.launcher.mummu.curymean.R;
import com.launcher.mummu.curymean.listeners.FirebaseAuthListener;

/**
 * Created by muhammed on 3/8/2017.
 */

public class GoogleSigin implements GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 1024;
    private static GoogleSigin googleSigin;
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private AppCompatActivity activity;
    private FirebaseAuthListener listener;
    private FirebaseAuth mAuth;

    private GoogleSigin() {
    }

    public static GoogleSigin getInstance() {
        return googleSigin == null ? googleSigin = new GoogleSigin() : googleSigin;
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }

    public void initialize(Context context, AppCompatActivity activity) {
        this.context = context;
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getContext().getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .enableAutoManage(activity, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (listener != null) {
            listener.onError();
        }
    }

    public void login(FirebaseAuthListener listener) {
        this.listener = listener;
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        getActivity().startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (listener != null) {
                    listener.onComplete(task);
                }
            }
        });
    }
}
