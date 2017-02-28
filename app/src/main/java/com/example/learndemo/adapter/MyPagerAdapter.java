package com.example.learndemo.adapter;

import android.os.Parcelable;
import android.print.PageRange;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/24.
 */

public class MyPagerAdapter extends PagerAdapter {

    private ArrayList<View> data;

    public ArrayList<View> getData() {
        return data;
    }

    public void setData(ArrayList<View> data) {
        this.data = data;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        Log.d("k", "destroyItem");
        ((ViewPager) arg0).removeView(data.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
        Log.d("k", "finishUpdate");
    }

    @Override
    public int getCount() {
        Log.d("k", "getCount");
        return data.size();
    }

    @Override

    public Object instantiateItem(View arg0, int arg1) {
        Log.d("k", "instantiateItem");
        String question_body_start = "<body bgcolor=\"#ffffff\">";
        String question_body = "<br><img src=\"https://www.baidu.com/img/bd_logo1.png\"/><div>上面这张是百度的图片</div>" +
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>"+
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>";
        String question_body_end = "</body>";
        WebView webView = (WebView) data.get(arg1);
        webView.loadData(question_body_start+"第"+(arg1+1)+"页"+question_body+question_body_end, "text/html; charset=UTF-8", "UTF-8");
        ((ViewPager) arg0).addView(data.get(arg1), 0);
        return data.get(arg1);
    }

    @Override

    public boolean isViewFromObject(View arg0, Object arg1) {
        Log.d("k", "isViewFromObject");
        return arg0 == (arg1);
    }

    @Override

    public void restoreState(Parcelable arg0, ClassLoader arg1) {
        Log.d("k", "restoreState");
    }

    @Override

    public Parcelable saveState() {
        Log.d("k", "saveState");
        return null;
    }

    @Override

    public void startUpdate(View arg0) {
        Log.d("k", "startUpdate");
    }
}
