package com.example.learndemo;

import android.app.Application;

/**
 * Created by Administrator on 2017/2/22.
 */

public class MyApp extends Application {

    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApp getMyApp() {
        return myApp;
    }

}
