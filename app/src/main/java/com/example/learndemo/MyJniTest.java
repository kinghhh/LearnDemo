package com.example.learndemo;

import android.util.Log;

/**
 * Created by Administrator on 2017/2/28.
 */

public class MyJniTest {
    static {
        //gradle ndk module名字
        System.loadLibrary("MyLibrary");
    }

    public native String getString();

    public int demoLog(String str){
        Log.i("UDP","这是我自己的demo : "+str);
        return 1;
    }

    public void voidMethod(String str){
        Log.i("UDP","这是一个没有返回值的方法");
    }
}
