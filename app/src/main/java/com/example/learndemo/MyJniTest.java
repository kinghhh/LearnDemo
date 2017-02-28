package com.example.learndemo;

/**
 * Created by Administrator on 2017/2/28.
 */

public class MyJniTest {
    static {
        //gradle ndk module名字
        System.loadLibrary("MyLibrary");
    }

    public native String getString();
}
