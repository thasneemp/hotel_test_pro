package com.launcher.mummu.curymean.authentication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.launcher.mummu.curymean.listeners.FirebaseAuthListener;

import java.util.Arrays;

/**
 * Created by muhammed on 3/8/2017.
 */

public class FacebookSign {
    private static FacebookSign mFacebook;
    private Context context;
    private CallbackManager callbackManager;
    private FirebaseAuthListener listener;
    private AppCompatActivity activity;
    private FirebaseAuth mAuth;
    FacebookCallback<LoginResult> facebookCallback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            handleFacebookAccessToken(loginResult.getAccessToken());
        }

        @Override
        public void onCancel() {
            if (listener != null) {
                listener.onError();
            }
        }

        @Override
        public void onError(FacebookException error) {
            if (listener != null) {
                listener.onError();
            }
        }
    };

    public static FacebookSign getInstance() {
        return mFacebook == null ? mFacebook = new FacebookSign() : mFacebook;
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
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, facebookCallback);
    }

    public void login(FirebaseAuthListener listener) {
        this.listener = listener;
        LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile,email"));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (listener != null) {
                            listener.onComplete(task);
                        }
                    }
                });
    }
}
