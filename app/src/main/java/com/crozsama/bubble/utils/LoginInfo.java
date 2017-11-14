package com.crozsama.bubble.utils;

import android.content.Context;
import android.util.Base64;

import com.crozsama.bubble.network.SignInResponse;
import com.google.gson.GsonBuilder;

public class LoginInfo {
    private static final int DEFAULT_TOKEN_EXPIRE_DURATION = 3600 * 4;
    private static final String SP_KEY_USER_INFO = "user_info";
    private static final String SP_KEY_TOKEN = "token";
    private static final String SP_KEY_TOKEN_EXPIRE = "token_expire";
    private static final String SP_KEY_PROFILE = "profile";

    private String username;
    private String password;
    private String token;
    private int expire;
    private SignInResponse.Profile profile;

    public LoginInfo(String username, String password, SignInResponse.Data data) {
        this.username = username;
        this.password = password;
        this.token = data.token;
        this.profile = data.profile;
        this.expire = (int) (System.currentTimeMillis() / 1000) + DEFAULT_TOKEN_EXPIRE_DURATION;
    }

    public LoginInfo(Context ctx) {
        String profileStr = (String) SPUtils.get(ctx, SP_KEY_PROFILE, "");
        String infoStr = (String) SPUtils.get(ctx, SP_KEY_USER_INFO, "");
        this.profile = new GsonBuilder().create().fromJson(profileStr, SignInResponse.Profile.class);
        this.token = (String) SPUtils.get(ctx, SP_KEY_TOKEN, "");
        this.expire = (int) SPUtils.get(ctx, SP_KEY_TOKEN_EXPIRE, 0);
        this.decode(infoStr);
    }

    public void save(Context ctx) {
        SPUtils.put(ctx, SP_KEY_USER_INFO, this.encode());
        SPUtils.put(ctx, SP_KEY_TOKEN, this.token);
        SPUtils.put(ctx, SP_KEY_TOKEN_EXPIRE, this.expire);
        SPUtils.put(ctx, SP_KEY_PROFILE, this.profile.toString());
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    public int getExpire() {
        return expire;
    }

    public SignInResponse.Profile getProfile() {
        return profile;
    }

    private void decode(String str) {
        String temp = new String(Base64.decode(str, Base64.URL_SAFE));
        temp = new StringBuffer(temp).reverse().toString();
        String[] array = temp.split("\n");
        if (array.length < 2) {
            this.username = "";
            this.password = "";
            return;
        }
        this.username = array[0];
        this.password = array[1];
    }

    private String encode() {
        String temp = new StringBuffer(String.format("%s\n%s", this.username, this.password)).reverse().toString();
        return Base64.encodeToString(temp.getBytes(), Base64.URL_SAFE);
    }
}
