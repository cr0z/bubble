package com.crozsama.bubble.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.crozsama.bubble.AppManager;

/**
 * Created by 93201 on 2017/11/7.
 */

public class BaseActivity extends AppCompatActivity {
    public static final int ACTIVITY_RESUMED = 0;
    public static final int ACTIVITY_STOPPED = 1;
    public static final int ACTIVITY_PAUSED = 2;
    public static final int ACTIVITY_DESTROYED = 3;

    private int activityState = ACTIVITY_DESTROYED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ACTIVITY_RESUMED;
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ACTIVITY_STOPPED;
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ACTIVITY_PAUSED;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityState = ACTIVITY_DESTROYED;
        AppManager.getAppManager().finishActivity(this);
    }
}
