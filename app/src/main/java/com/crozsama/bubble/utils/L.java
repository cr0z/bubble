package com.crozsama.bubble.utils;

import android.util.Log;

/**
 * Created by 93201 on 2017/11/8.
 */

public class L {
    private L() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static void d(String tag, String str) {
        if (BuildConfig.DEBUG)
            Log.d(tag, str);
    }

    public static void e(String tag, String str) {
        if (BuildConfig.DEBUG)
            Log.e(tag, str);
    }

    public static void i(String tag, String str) {
        if (BuildConfig.DEBUG)
            Log.i(tag, str);
    }

    public static void w(String tag, String str) {
        if (BuildConfig.DEBUG)
            Log.w(tag, str);
    }

    public static void v(String tag, String str) {
        if (BuildConfig.DEBUG)
            Log.v(tag, str);
    }
}
