package com.crozsama.bubble.network;

import android.content.Context;

import com.crozsama.bubble.utils.Crypto;
import com.crozsama.bubble.utils.LoginInfo;
import com.crozsama.bubble.utils.SPUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static final String BASE_API_ADDR = "http://192.168.0.114:10086";

    private static final String SIGN_IN = "/api/signin";
    private static final String SIGN_UP = "/api/signup";
    private static final String SIGN_OUT = "/api/signout";
    private static final String BUBBLE_POST = "/api/bubble/post";
    private static final String BUBBLE_LIKE = "/api/bubble/like";
    private static final String COMMENT_POST = "/api/comment/post";
    private static final String SEND_CAPTCHA_EMAIL = "/api/captcha/email";
    private static final String USER_SETTINGS = "/api/u/settings";
    private static final String USER_PROFILE_EDIT = "/api/u/profile/%s/edit";
    private static final String USER_AVATAR_CHANGE = "/api/u/avatar/change";
    private static final String USER_SECURITY_EMAIL_CHANGE = "/api/u/security/email/change";
    private static final String USER_SECURITY_PSWD_RESET = "/api/u/security/pswd/reset";
    private static final String USER_SECURITY_PSWD_CHANGE = "/api/u/security/pswd/change";
    private static final String USER_SECURITY_VERIFY_EMAIL = "/api/u/security/";
    private static final String USER_BUBBLES = "/api/u/%s/bubbles";
    private static final String USER_TOKEN_VERIFY = "/api/u/token/verify";
    private static final String USER_DYNAMICS = "/api/dynamics";
    private static final String COMMENT_LIST = "/api/comment/list";
    private static final String USER_AVATAR = "/avatar/%s/%s";
    private static final String USER_COVER = "/cover/%s/%s";
    private static final String CAPTCHA = "/captcha/%s";

    private static String token = "";

    public static String getSignInUrl() {
        return BASE_API_ADDR + SIGN_IN;
    }

    public static String getSignUpUrl() {
        return BASE_API_ADDR + SIGN_UP;
    }

    public static String getSignOutUrl() {
        return BASE_API_ADDR + SIGN_OUT;
    }

    public static String getBubblePostUrl() {
        return BASE_API_ADDR + BUBBLE_POST;
    }

    public static String getBubbleLikeUrl() {
        return BASE_API_ADDR + BUBBLE_LIKE;
    }

    public static String getCommentPostUrl() {
        return BASE_API_ADDR + COMMENT_POST;
    }

    public static String getSendCaptchaEmailUrl() {
        return BASE_API_ADDR + SEND_CAPTCHA_EMAIL;
    }

    public static String getUserSettingsUrl() {
        return BASE_API_ADDR + USER_SETTINGS;
    }

    public static String getUserProfileEditUrl(String username) {
        return BASE_API_ADDR + String.format(USER_PROFILE_EDIT, username);
    }

    public static String getUserTokenVerify() {
        return BASE_API_ADDR + USER_TOKEN_VERIFY;
    }

    public static String getUserAvatarChangeUrl() {
        return BASE_API_ADDR + USER_AVATAR_CHANGE;
    }

    public static String getUserSecurityEmailChangeUrl() {
        return BASE_API_ADDR + USER_SECURITY_EMAIL_CHANGE;
    }

    public static String getUserSecurityPswdResetUrl() {
        return BASE_API_ADDR + USER_SECURITY_PSWD_RESET;
    }

    public static String getUserSecurityPswdChangeUrl() {
        return BASE_API_ADDR + USER_SECURITY_PSWD_CHANGE;
    }

    public static String getUserSecurityVerifyEmailUrl() {
        return BASE_API_ADDR + USER_SECURITY_VERIFY_EMAIL;
    }

    public static String getUserBubblesUrl(String username) {
        return BASE_API_ADDR + String.format(USER_BUBBLES, username);
    }

    public static String getUserDynamicsUrl() {
        return BASE_API_ADDR + USER_DYNAMICS;
    }

    public static String getCommentListUrl() {
        return BASE_API_ADDR + COMMENT_LIST;
    }

    public static String getUserAvatarUrl(String username) {
        return BASE_API_ADDR + String.format(USER_AVATAR, username, username);
    }

    public static String getUserCoverUrl(String username) {
        return BASE_API_ADDR + String.format(USER_COVER, username, username);
    }

    public static String getImageCaptchaUrl(String captchaID) {
        return BASE_API_ADDR + String.format(CAPTCHA + ".png", captchaID);
    }

    public static String getAudioCaptchaUrl(String captchaID) {
        return BASE_API_ADDR + String.format(CAPTCHA + ".wav", captchaID);
    }

    private static OkHttpClient client = new OkHttpClient();

    private Api() {
    }

    public static String get(String url) throws IOException {
        Request request = new Request.Builder().
                url(url).
                header("Authorization", token).
                build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String post(String url, Map<String, String>... requestForms) throws IOException {
        FormBody.Builder builder = new FormBody.Builder();
        if (requestForms.length > 0) {
            Map<String, String> reqForm = requestForms[0];
            for (String key : reqForm.keySet()) {
                builder = builder.add(key, reqForm.get(key));
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().
                url(url).
                header("Authorization", token).
                post(body).
                build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static SignInResponse signIn(Context ctx, String username, String password) {
        Map<String, String> form = new HashMap<>();
        form.put("username", username);
        form.put("password", Crypto.getMD5(password));
        String responseStr;
        try {
            responseStr = Api.post(Api.getSignInUrl(), form);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new GsonBuilder().create();
        SignInResponse resp = gson.fromJson(responseStr, SignInResponse.class);
        if (resp.code == ErrorCode.CODE_OK) {
            token = resp.data.token;
            new LoginInfo(username, password, resp.data).save(ctx); //保存登录信息到本地
        }
        return resp;
    }

//    public static boolean verifyToken(String str) {
//        token = str;
//        String responseStr;
//        try {
//            responseStr = post(getUserTokenVerify(), new HashMap<String, String>());
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//        Gson gson = new GsonBuilder().create();
//        BaseResponse resp = gson.fromJson(responseStr, BaseResponse.class);
//        return resp.code == ErrorCode.CODE_OK;
//    }

    public static void setToken(String to) {
        token = to;
    }

    public enum Method {
        GET,
        POST
    }
}