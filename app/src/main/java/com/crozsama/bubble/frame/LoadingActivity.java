package com.crozsama.bubble.frame;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.crozsama.bubble.R;
import com.crozsama.bubble.base.BaseActivity;
import com.crozsama.bubble.network.Api;
import com.crozsama.bubble.network.SignInResponse;
import com.crozsama.bubble.utils.SPUtils;
import com.google.gson.GsonBuilder;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        checkLogin();
    }


    private void checkLogin() {
        Intent intent;
        String token = (String) SPUtils.get(getApplicationContext(), "token", "");
        if (!TextUtils.isEmpty(token)) {
            //TODO verify token
            Api.setToken(token);
            String profileStr = (String) SPUtils.get(getApplicationContext(), "profile", "");
            SignInResponse.Profile profile = new GsonBuilder().create().
                    fromJson(profileStr, SignInResponse.Profile.class);
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("profile", profile);
            intent.putExtra("token", token);
        } else {
            //TODO 自动登录
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
