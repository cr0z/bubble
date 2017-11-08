package com.crozsama.bubble.network;

import android.content.Context;

import com.crozsama.bubble.R;

/**
 * Created by 93201 on 2017/11/8.
 */

public class ErrorCode {
    public static final int CODE_OK = 100;
    public static final int CODE_PARAM_INVALID = 201;
    public static final int CODE_USERNAME_INVALID = 202;
    public static final int CODE_PASSWORD_INVALID = 203;
    public static final int CODE_EMAIL_INVALID = 204;
    public static final int CODE_MOBILE_INVALID = 205;
    public static final int CODE_FILE_TOO_LARGE = 206;
    public static final int CODE_OBJECT_EXISTED = 210;
    public static final int CODE_USERNAME_EXISTED = 211;
    public static final int CODE_EMAIL_EXISTED = 212;
    public static final int CODE_MOBILE_EXISTED = 213;
    public static final int CODE_NOT_EXISTED = 220;
    public static final int CODE_NOT_ACTIVE = 301;
    public static final int CODE_PERMISSION_DENIED = 302;
    public static final int CODE_ACCOUNT_ABNORMAL = 303;
    public static final int CODE_FROZEN = 304;
    public static final int CODE_TOKEN_EXPIRE = 311;
    public static final int CODE_TOKEN_INVALID = 312;
    public static final int CODE_SEND_EMAIL_FAILED = 410;
    public static final int CODE_CAPTCHA_INVALID = 420;
    public static final int CODE_PAGE_INVALID = 430;
    public static final int CODE_SYS_ERROR = -1;
    public static final int CODE_DB_ERROR = -2;
    public static final int CODE_UP_TO_LIMIT = -3;
    public static final int CODE_SERVICE_NOT_AVAILABLE = -4;


    public static String getErrorString(Context ctx, int code) {
        String str = ctx.getString(R.string.code_unknown);
        switch (code) {
            case CODE_OK:
                str = ctx.getString(R.string.code_ok);
                break;
            case CODE_PARAM_INVALID:
                str = ctx.getString(R.string.code_param_invalid);
                break;
            case CODE_USERNAME_INVALID:
                str = ctx.getString(R.string.code_username_invalid);
                break;
            case CODE_PASSWORD_INVALID:
                str = ctx.getString(R.string.code_password_invalid);
                break;
            case CODE_EMAIL_INVALID:
                str = ctx.getString(R.string.code_email_invalid);
                break;
            case CODE_MOBILE_INVALID:
                str = ctx.getString(R.string.code_mobile_invalid);
                break;
            case CODE_FILE_TOO_LARGE:
                str = ctx.getString(R.string.code_file_too_large);
                break;
            case CODE_OBJECT_EXISTED:
                str = ctx.getString(R.string.code_object_existed);
                break;
            case CODE_USERNAME_EXISTED:
                str = ctx.getString(R.string.code_username_existed);
                break;
            case CODE_EMAIL_EXISTED:
                str = ctx.getString(R.string.code_email_existed);
                break;
            case CODE_MOBILE_EXISTED:
                str = ctx.getString(R.string.code_mobile_existed);
                break;
            case CODE_NOT_EXISTED:
                str = ctx.getString(R.string.code_not_existed);
                break;
            case CODE_NOT_ACTIVE:
                str = ctx.getString(R.string.code_not_active);
                break;
            case CODE_PERMISSION_DENIED:
                str = ctx.getString(R.string.code_permission_denied);
                break;
            case CODE_ACCOUNT_ABNORMAL:
                str = ctx.getString(R.string.code_account_abnormal);
                break;
            case CODE_FROZEN:
                str = ctx.getString(R.string.code_frozen);
                break;
            case CODE_TOKEN_EXPIRE:
                str = ctx.getString(R.string.code_token_expire);
                break;
            case CODE_TOKEN_INVALID:
                str = ctx.getString(R.string.code_token_invalid);
                break;
            case CODE_SEND_EMAIL_FAILED:
                str = ctx.getString(R.string.code_send_email_failed);
                break;
            case CODE_CAPTCHA_INVALID:
                str = ctx.getString(R.string.code_captcha_invalid);
                break;
            case CODE_PAGE_INVALID:
                str = ctx.getString(R.string.code_page_invalid);
                break;
            case CODE_SYS_ERROR:
                str = ctx.getString(R.string.code_sys_error);
                break;
            case CODE_DB_ERROR:
                str = ctx.getString(R.string.code_db_error);
                break;
            case CODE_UP_TO_LIMIT:
                str = ctx.getString(R.string.code_up_to_limit);
                break;
            case CODE_SERVICE_NOT_AVAILABLE:
                str = ctx.getString(R.string.code_service_not_available);
                break;
        }
        return str;
    }
}
