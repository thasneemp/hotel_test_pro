package com.launcher.mummu.curymean.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.launcher.mummu.curymean.R;

/**
 * Created by muhammed on 3/8/2017.
 */

public class MainActivity extends ContainerActivity {
    private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        setUI();
    }

    private void setUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }
}
