package com.crozsama.bubble.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by 93201 on 2017/11/8.
 */

public class Crypto {
    public static String getMD5(String s) {
        String result = null;
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(s.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 should be supported?", e);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        result = hex.toString();

        return result;
    }
}
