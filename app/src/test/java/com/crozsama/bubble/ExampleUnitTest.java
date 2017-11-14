package com.crozsama.bubble;

import com.crozsama.bubble.utils.Crypto;

import org.junit.Test;

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
    public void utilsTest() throws Exception {
        String str = Crypto.reverseConfusion("croz\ncroz");
        System.out.print(str);
        str = Crypto.unreverseConfusion(str);
        System.out.print(str);
        assertEquals(str, "croz\ncroz");
    }
}