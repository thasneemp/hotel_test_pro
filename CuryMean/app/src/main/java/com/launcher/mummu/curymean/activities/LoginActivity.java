package com.launcher.mummu.curymean.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.launcher.mummu.curymean.R;
import com.launcher.mummu.curymean.authentication.FacebookSign;
import com.launcher.mummu.curymean.authentication.GoogleSigin;
import com.launcher.mummu.curymean.listeners.FirebaseAuthListener;

/**
 * Created by muhammed on 3/8/2017.
 */

public class LoginActivity extends ContainerActivity implements View.OnClickListener, FirebaseAuthListener {
    private Button mGoogleSiginButton;
    private Button mFacebookSiginButton;
    private GoogleSigin mGoogleSigininstance;
    private FacebookSign facebookSign;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        setUI();
        mGoogleSigininstance = GoogleSigin.getInstance();
        facebookSign = FacebookSign.getInstance();
        mGoogleSigininstance.initialize(this, this);
        facebookSign.initialize(this, this);
    }

    private void setUI() {
        mGoogleSiginButton = (Button) findViewById(R.id.googleSiginButton);
        mFacebookSiginButton = (Button) findViewById(R.id.facebookButton);
        mGoogleSiginButton.setOnClickListener(this);
        mFacebookSiginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.googleSiginButton:
                mGoogleSigininstance.login(this);
                break;
            default:
                facebookSign.login(this);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogleSigininstance.onActivityResult(requestCode, resultCode, data);
        facebookSign.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            startActivity(new Intent(this, MainActivity.class));
        } else {
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onError() {

    }
}
