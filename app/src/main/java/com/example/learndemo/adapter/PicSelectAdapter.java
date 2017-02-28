package com.example.learndemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.learndemo.R;
import com.example.learndemo.util.AlbumBitmapCacheHelper;
import com.example.learndemo.util.PicBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public class PicSelectAdapter extends RecyclerView.Adapter<PicSelectAdapter.MyHolder> {

    private ArrayList<PicBean> data;
    private boolean isAll;
    private RecyclerView recyclerView;
    private ViewGroup parent;
    private OnItemClickListener itemClickListener;
    private ArrayList<String> select_pic;

    public interface OnItemClickListener{
        public void itemclick(View view, int position);
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public ArrayList<PicBean> getData() {
        return data;
    }

    public void setData(ArrayList<PicBean> data) {
        this.data = data;
    }

    public ArrayList<String> getSelect_pic() {
        return select_pic;
    }

    public void setSelect_pic(int index) {
        PicBean temp = data.get(index);
        if (!select_pic.contains(temp.getPath())){
            if (select_pic.size() == 9){
                return;
            }
            select_pic.add(temp.getPath());
        } else {
            select_pic.remove(temp.getPath());
        }
        notifyDataSetChanged();
    }

    public PicSelectAdapter(RecyclerView recyclerView) {
        data = new ArrayList<>();
        select_pic = new ArrayList<>();
        this.recyclerView = recyclerView;
    }

    @Override
    public PicSelectAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.activity_select_pic_layout_item,null);
        this.parent = parent;
        MyHolder holder = new MyHolder(view);
        ViewGroup.LayoutParams layoutParams = holder.iv_pic.getLayoutParams();
        layoutParams.height = parent.getWidth()/3;
        layoutParams.width = parent.getWidth()/3;
        holder.iv_pic.setLayoutParams(layoutParams);
        return holder;
    }

    @Override
    public void onBindViewHolder(PicSelectAdapter.MyHolder holder, final int position) {
        //如果自定义的itemclick监听不为空，则设置该项中占地最大的一个view的onclick
        if (itemClickListener != null){
            holder.iv_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.itemclick(v,position);
                }
            });
        }

        if (select_pic.contains(data.get(position).getPath())){
            holder.iv_select.setVisibility(View.VISIBLE);
        } else {
            holder.iv_select.setVisibility(View.GONE);
        }

        if (holder.iv_pic.getTag() != null) {
            AlbumBitmapCacheHelper.getInstance().removePathFromShowlist((String) (holder.iv_pic.getTag()));
        }
        String path = data.get(position).getPath();
        if ("-1".equals(path)){
            holder.iv_pic.setImageResource(R.mipmap.add_pic);
            return;
        }
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
                    v = recyclerView.findViewWithTag(path);
                }
                if (v != null) ((ImageView)v).setImageBitmap(bitmap);
            }
        }, isAll);
        if (bitmap != null){
//            BitmapDrawable bd = new BitmapDrawable(context.getResources(), bitmap);
            holder.iv_pic.setImageBitmap(bitmap);
        }else{
            holder.iv_pic.setImageResource(R.mipmap.default_loading);
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
        }
    }
}
