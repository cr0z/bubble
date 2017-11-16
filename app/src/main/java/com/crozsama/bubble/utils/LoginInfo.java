package com.crozsama.bubble.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;

import com.crozsama.bubble.network.SignInResponse;
import com.google.gson.GsonBuilder;

import javax.crypto.SecretKey;

public class LoginInfo {
    private static final int DEFAULT_TOKEN_EXPIRE_DURATION = 3600 * 4;
    private static final String SP_KEY_TOKEN = "token";
    private static final String SP_KEY_TOKEN_EXPIRE = "token_expire";
    private static final String SP_KEY_PROFILE = "profile";
    private static final String SP_KEY_USERNAME = "username";
    private static final String SP_KEY_PASSWORD = "password";

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
        this.profile = new GsonBuilder().create().fromJson(profileStr, SignInResponse.Profile.class);
        this.token = (String) SPUtils.get(ctx, SP_KEY_TOKEN, "");
        this.expire = (int) SPUtils.get(ctx, SP_KEY_TOKEN_EXPIRE, 0);
        this.username = (String) SPUtils.get(ctx, SP_KEY_USERNAME, "");
        this.password = this.decodePassword((String) SPUtils.get(ctx, SP_KEY_PASSWORD, ""));
    }

    public void save(Context ctx) {
        SPUtils.put(ctx, SP_KEY_TOKEN, this.token);
        SPUtils.put(ctx, SP_KEY_TOKEN_EXPIRE, this.expire);
        SPUtils.put(ctx, SP_KEY_PROFILE, this.profile.toString());
        SPUtils.put(ctx, SP_KEY_USERNAME, this.username);
        SPUtils.put(ctx, SP_KEY_PASSWORD, this.encodePassword());
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

    private String decodePassword(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        byte[] b = Base64.decode(str, Base64.URL_SAFE);
        return Crypto.aesECBDecode(b, this.getSecretKey());
    }

    private String encodePassword() {
        if (TextUtils.isEmpty(this.password)) {
            return "";
        }
        byte[] b = Crypto.aesECBEncode(this.password.getBytes(), this.getSecretKey());
        return Base64.encodeToString(b, Base64.URL_SAFE);
    }

    private SecretKey getSecretKey() {
        return Crypto.restoreSecretKey(Crypto.getMD5(this.username));
    }

    public static String getStoredUsername(Context ctx) {
        return (String) SPUtils.get(ctx, SP_KEY_USERNAME, "");
    }
}
