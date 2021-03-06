package com.crozsama.bubble.frame;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.crozsama.bubble.R;
import com.crozsama.bubble.base.BaseActivity;
import com.crozsama.bubble.network.Api;
import com.crozsama.bubble.network.ErrorCode;
import com.crozsama.bubble.network.SignInResponse;
import com.crozsama.bubble.task.LoginTask;
import com.crozsama.bubble.utils.LoginInfo;

public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        checkLogin();
    }

    private void checkLogin() {
        //读取登录信息
        final LoginInfo info = new LoginInfo(getApplication());

        //未读取到保存的用户信息，跳转到登录界面
        if (TextUtils.isEmpty(info.getUsername()) || TextUtils.isEmpty(info.getPassword())) {
            toLoginActivity();
            return;
        }

        //保存的用户信息未过期，跳转到主界面
        if (System.currentTimeMillis() / 1000 < info.getExpire() && !TextUtils.isEmpty(info.getToken())) {
            Api.setToken(info.getToken());
            toMainActivity(info.getProfile());
            return;
        }

        //保存的用户信息已过期，尝试自动登录
        LoginTask task = new LoginTask(getApplicationContext(), info.getUsername(), info.getPassword());
        task.setOnPostExecuted((response) -> {
            if (response.code == ErrorCode.CODE_OK) {
                //自动登录成功，跳转主界面
                toMainActivity(response.data.profile);
            } else {
                //自动登录失败，跳转登录界面
                toLoginActivity();
            }
        }).execute();

    }

    private void toLoginActivity() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void toMainActivity(SignInResponse.Profile profile) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("profile", profile);
        startActivity(intent);
        finish();
    }
}
