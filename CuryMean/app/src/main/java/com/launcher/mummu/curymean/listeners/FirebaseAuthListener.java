package com.launcher.mummu.curymean.listeners;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

/**
 * Created by muhammed on 3/8/2017.
 */

public interface FirebaseAuthListener {
    void onComplete(@NonNull Task<AuthResult> task);

    void onError();
}
