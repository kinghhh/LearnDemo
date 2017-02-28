package com.example.learndemo.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learndemo.R;
import com.example.learndemo.adapter.PicSelectAdapter;
import com.example.learndemo.recyclerviewdemo.DividerItemDecoration;
import com.example.learndemo.util.EventBusBean;
import com.example.learndemo.util.PicBean;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/2/22.
 */

public class SelectPicActivity extends Activity implements View.OnClickListener{

    private RecyclerView rv_select_pics;
    private ArrayList<PicBean> allPictures;//所有图片
    private HashMap<String, ArrayList<PicBean>> dirPictures;//所有包含图片的路径
    private TextView tv_select_pic,tv_back;
    private DrawerLayout drawerlayout;
    private ListView lv_dir;
    private String curDir;
    private Button btn_send;
    private String selectedPath="";//当前被选中的图片的路径，点击确认发送时，需要读取该路径下的文件，保存至指定文件夹下
    private Dialog dialog;
    private PicSelectAdapter picSelectAdapter;

    public static final int HANDLER_LOADPIC_FINISH = 0x010;

    Handler mHandler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLER_LOADPIC_FINISH:
                    curDir = "all";
                    picSelectAdapter.setData(allPictures);
                    picSelectAdapter.notifyDataSetChanged();
                    tv_select_pic.setClickable(true);
                    break;

                default:
                    break;
            }
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic_layout);

        rv_select_pics = (RecyclerView) findViewById(R.id.rv_select_pics);
        tv_select_pic = (TextView) findViewById(R.id.tv_select_pic);
        tv_back = (TextView) findViewById(R.id.tv_back);
        lv_dir = (ListView) findViewById(R.id.lv_dir);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        btn_send = (Button) findViewById(R.id.btn_send);

        tv_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);

        //设置布局管理器，LinearLayoutManager 现行管理器，支持横向、纵向。 GridLayoutManager 网格布局管理器 StaggeredGridLayoutManager 瀑布就式布局管理器
        rv_select_pics.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        //设置recyclerview每一项中间的间隔
        rv_select_pics.addItemDecoration(new DividerItemDecoration(this));

        //通过该方法可以关闭右边侧滑栏，但是手势无效
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

        allPictures = new ArrayList<>();
        dirPictures = new HashMap<>();

        picSelectAdapter = new PicSelectAdapter(rv_select_pics);
        picSelectAdapter.setItemClickListener(new PicSelectAdapter.OnItemClickListener() {
            @Override
            public void itemclick(View view, int position) {
                picSelectAdapter.setSelect_pic(position);
            }
        });
        rv_select_pics.setAdapter(picSelectAdapter);

        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();

        lv_dir.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                selectedPath = "";
//                curDir = picDirAdapter.getItem((int)arg3).getKey();
//                picDirAdapter.setCurentDir(curDir);
//                adapter.setSelectedIndex(-1);
//                if ((int)arg3 == 0){
//                    tv_select_pic.setText("所有图片");
//                    adapter.setData(allPictures);
//                } else {
//                    tv_select_pic.setText(MyUtils.getFileName(curDir));
//                    adapter.setData(picDirAdapter.getItem((int)arg3).getValue());
//                }
//                adapter.notifyDataSetChanged();
//                picDirAdapter.notifyDataSetChanged();
                drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });

        tv_select_pic.setClickable(false);//在获取到图片之前，禁止点击图片选择
        tv_select_pic.setOnClickListener(this);

        getImagePath();

    }

    /**
     * 获取图片
     */
    public void getImagePath(){
        new Thread(){
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = getContentResolver();
                //
                Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=\"image/jpeg\" or " +
                        MediaStore.Images.Media.MIME_TYPE + "=\"image/png\"", null, MediaStore.Images.Media.DATE_MODIFIED+" desc");
                if (cursor != null) {
                    allPictures.clear();
                    while (cursor.moveToNext()) {
                        PicBean tempPic = new PicBean();
                        tempPic.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                        try {
                            tempPic.setDate(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
                        }catch (NumberFormatException e){
                            tempPic.setDate(System.currentTimeMillis()+"");
                        }
                        try {
                            tempPic.set_id(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)));
                        }catch (NumberFormatException e){
                            tempPic.set_id("0");
                        }
                        allPictures.add(tempPic);
                        String dirPath = new File(tempPic.getPath()).getParent();
                        if (dirPictures.get(dirPath) == null) {
                            ArrayList<PicBean> tempPics = new ArrayList<>();
                            tempPics.add(tempPic);
                            dirPictures.put(dirPath, tempPics);
                        } else {
                            dirPictures.get(dirPath).add(tempPic);
                        }
                    }
                    mHandler.sendEmptyMessage(HANDLER_LOADPIC_FINISH);
                }
            };
        }.start();
    }

    private void showDialog(){
        if (TextUtils.isEmpty(selectedPath)){
            Toast.makeText(this,"请先选择图片",Toast.LENGTH_SHORT).show();
            return;
        }
        if (dialog != null){
            dialog.dismiss();
            dialog = null;
        }

//        dialog = new Dialog(this,R.style.BlackScreenDialog);
        ProgressBar bar = new ProgressBar(this);
        dialog.setContentView(bar);
        dialog.setCancelable(false);
        dialog.show();
        new Thread(){
            @Override
            public void run() {
                savePic();
            }
        }.start();
    }

    /**
     * 点击发送后，判断图片是否大于1M，如果大于，则压缩
     * 然后保存到指定的路径下
     */
    private void savePic(){
        try {
            String picpath = "";
            FileOutputStream fos = new FileOutputStream(picpath);
            File picFile = new File(selectedPath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            if (picFile.length() > (500 * 1024)){
                options.inSampleSize = (int) (picFile.length()/(500*1024));
            }
            Bitmap tempBit = BitmapFactory.decodeFile(selectedPath,options);
            tempBit.compress(Bitmap.CompressFormat.PNG,100,fos);
            tempBit.recycle();
            tempBit = null;
            Intent data = new Intent();
            data.putExtra("picpath",picpath);
            setResult(RESULT_OK,data);
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_select_pic:
                if (lv_dir.getVisibility() == View.VISIBLE) {
                    drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
                } else {
                    drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN, Gravity.RIGHT);
                }
                break;
            case R.id.tv_back:
                setResult(RESULT_CANCELED);
                finish();
                break;
            case R.id.btn_send:
                EventBusBean tempBean = new EventBusBean();
                tempBean.setData(picSelectAdapter.getSelect_pic());
                EventBus.getDefault().post(tempBean);
                finish();
//                showDialog();
//			savePic();
                break;

            default:
                break;
        }
    }
}
