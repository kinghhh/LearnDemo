package com.example.learndemo.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.learndemo.R;
import com.example.learndemo.adapter.MyPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/24.
 */

public class ViewPagerWebviewActivity extends Activity {

    private ViewPager vp_contain;
    private TextView tv_page;
    private ArrayList<View> mListViews;
    private MyPagerAdapter myPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager_layout);

        findView();

        Log.i("UDP","start : "+System.currentTimeMillis());
        mListViews = new ArrayList<>();
        addWebview();
        Log.i("UDP","start : "+System.currentTimeMillis());

        myPagerAdapter = new MyPagerAdapter();
        myPagerAdapter.setData(mListViews);
        vp_contain.setAdapter(myPagerAdapter);

        tv_page.setText("1/"+mListViews.size());
        tv_page.setTextColor(Color.BLACK);

        vp_contain.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            //arg0 ==1的时候表示正在滑动，arg0==2的时候表示滑动完毕了，arg0==0的时候表示什么都没做，就是停在那。
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 2){
                    tv_page.setText((vp_contain.getCurrentItem()+1)+"/"+mListViews.size());
                }
            }
        });

    }

    private void addWebview(){
        String question_body_start = "<body bgcolor=\"#ffffff\">";
        String question_body = "<br><img src=\"https://www.baidu.com/img/bd_logo1.png\"/><div>上面这张是百度的图片</div>" +
                "<br><img src=\"https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=3018806800,4065630902&fm=96\"/><div>这是一张搜狗的图片，不得不说low</div>";
        String question_body_end = "</body>";
        for (int i=0; i<100; i++){
            WebView webView = new WebView(this);
//            webView.loadData(question_body_start+"第"+i+"页"+question_body+question_body_end, "text/html; charset=UTF-8", "UTF-8");
            mListViews.add(webView);
        }
    }

    private void findView(){
        vp_contain = (ViewPager) findViewById(R.id.vp_contain);
        tv_page = (TextView) findViewById(R.id.tv_page);
    }
}
