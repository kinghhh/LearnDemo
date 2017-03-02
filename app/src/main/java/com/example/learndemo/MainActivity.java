package com.example.learndemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.learndemo.ListDemo.MyListViewAdapter;
import com.example.learndemo.ListDemo.MyListViewDemoActivity;
import com.example.learndemo.activity.SelectPicActivity;
import com.example.learndemo.activity.ViewPagerWebviewActivity;
import com.example.learndemo.adapter.PicSelectAdapter;
import com.example.learndemo.adapter.PicShowAdapter;
import com.example.learndemo.recyclerviewdemo.DividerItemDecoration;
import com.example.learndemo.util.EventBusBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_1,btn_pager,btn_list_demo;
    private RecyclerView rv_pics;
    private PicShowAdapter picShowAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);

        MyJniTest test = new MyJniTest();

        Log.i("UDP","My jni test : "+test.getString());

        findView();
    }

    @Subscribe
    public void onEvent(EventBusBean bean) {
//        if (picShowAdapter.getItemCount() )
        for (int i=0;i<bean.getData().size(); i++){
//            rv_pics.setItemAnimator(new SlideInOutLeftItemAnimator(rv_pics));
            picShowAdapter.getData().add(0,bean.getData().get(i));
            picShowAdapter.notifyItemInserted(0);
            picShowAdapter.notifyItemRangeChanged(0,picShowAdapter.getItemCount());
        }

    }

    private void findView(){
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_pager = (Button) findViewById(R.id.btn_vpager);
        btn_list_demo = (Button) findViewById(R.id.btn_list_demo);
        rv_pics = (RecyclerView) findViewById(R.id.rv_pics);

        btn_1.setOnClickListener(this);
        btn_pager.setOnClickListener(this);
        btn_list_demo.setOnClickListener(this);

        picShowAdapter = new PicShowAdapter(rv_pics);
        picShowAdapter.getData().add("-1");
        rv_pics.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
//        rv_pics.addItemDecoration(new DividerItemDecoration(this));
        rv_pics.setAdapter(picShowAdapter);
        picShowAdapter.setItemClickListener(new PicSelectAdapter.OnItemClickListener() {
            @Override
            public void itemclick(View view, int position) {
                Log.i("UDP","position : "+picShowAdapter.getData().get(position)+" :::::::::::: "+position);
                if ("-1".equals(picShowAdapter.getData().get(position))){
                    startActivity(new Intent(MainActivity.this, SelectPicActivity.class));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_1:
                startActivity(new Intent(this, SelectPicActivity.class));
                break;
            case R.id.btn_vpager:
                startActivity(new Intent(this, ViewPagerWebviewActivity.class));
                break;
            case R.id.btn_list_demo:
                startActivity(new Intent(this, MyListViewDemoActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
