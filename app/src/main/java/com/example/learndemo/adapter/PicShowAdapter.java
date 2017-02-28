package com.example.learndemo.adapter;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.learndemo.R;
import com.example.learndemo.util.AlbumBitmapCacheHelper;
import com.example.learndemo.util.PicBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/25.
 */

public class PicShowAdapter extends  RecyclerView.Adapter<PicShowAdapter.MyHolder>{
    private ArrayList<String> data;
    private boolean isAll;
    private RecyclerView recyclerView;
    private ViewGroup parent;
    private PicSelectAdapter.OnItemClickListener itemClickListener;

    public interface OnItemClickListener{
        public void itemclick(View view, int position);
    }

    public PicSelectAdapter.OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(PicSelectAdapter.OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public void setData(ArrayList<String> data) {
        this.data = data;
    }

    public PicShowAdapter(RecyclerView recyclerView) {
        data = new ArrayList<>();
        this.recyclerView = recyclerView;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.activity_select_pic_layout_item,null);
        this.parent = parent;
        MyHolder holder = new MyHolder(view);
        ViewGroup.LayoutParams layoutParams = holder.iv_pic.getLayoutParams();
        layoutParams.height = parent.getWidth()/3;
        layoutParams.width = parent.getWidth()/3;
        holder.iv_pic.setLayoutParams(layoutParams);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicShowAdapter.MyHolder holder, final int position) {
        Log.i("UDP","itemClickListener : "+itemClickListener);
        //如果自定义的itemclick监听不为空，则设置该项中占地最大的一个view的onclick
        if (itemClickListener != null){
            holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemclick(v,position);
                }
            });
        }

        if (holder.iv_pic.getTag() != null) {
            AlbumBitmapCacheHelper.getInstance().removePathFromShowlist((String) (holder.iv_pic.getTag()));
        }
        String path = data.get(position);
        AlbumBitmapCacheHelper.getInstance().addPathToShowlist(path);
        if(isAll) {
            holder.iv_pic.setTag(path + "all");
        }
        else{
            holder.iv_pic.setTag(path);
        }
        Bitmap bitmap = AlbumBitmapCacheHelper.getInstance().getBitmap("",path, (parent.getWidth()/3)-20, parent.getHeight()/3-20, new AlbumBitmapCacheHelper.ILoadImageCallback() {
            @Override
            public void onLoadImageCallBack(Bitmap bitmap, String path, Object... objects) {
                if (bitmap == null) return;
//                BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
                View v = null;
                if ((Boolean)objects[0]) {
                    v = recyclerView.findViewWithTag(path + "all");
                } else {
                    Log.i("UDP","path : "+path);
                    v = recyclerView.findViewWithTag(path);
                }
                if (v != null) ((ImageView)v).setImageBitmap(bitmap);
            }
        }, isAll);
        if (bitmap != null){
//            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
            holder.iv_pic.setImageBitmap(bitmap);
        }else{
            if ("-1".equals(path)){
                Log.i("UDP","path : -1");
                holder.iv_pic.setImageResource(R.mipmap.add_pic);
            } else {
                holder.iv_pic.setImageResource(R.mipmap.default_loading);
            }
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        private ImageView iv_pic,iv_select;

        public MyHolder(View itemView) {
            super(itemView);
            iv_pic = (ImageView) itemView.findViewById(R.id.iv_pic);
            iv_select = (ImageView) itemView.findViewById(R.id.iv_select);

            iv_select.setVisibility(View.GONE);
        }
    }
}
