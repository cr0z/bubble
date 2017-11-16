package com.crozsama.bubble;

import com.crozsama.bubble.utils.Crypto;

import junit.framework.Assert;

import org.junit.Test;

import javax.crypto.SecretKey;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void aesECBTest() throws Exception {
        String content = "xingchen";
        byte[] keyBytes = Crypto.generateAESSecretKey();
        SecretKey key = Crypto.restoreSecretKey(keyBytes);
        byte[] res = Crypto.aesECBEncode(content.getBytes(), key);
        System.out.println(Crypto.toHexString(keyBytes));
        System.out.println(Crypto.toHexString(res));
        System.out.println(Crypto.aesECBDecode(res, key));
        Assert.assertEquals(Crypto.aesECBDecode(res, key), content);
    }
}