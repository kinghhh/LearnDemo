package com.example.learndemo.ListDemo;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.learndemo.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2.
 */

public class MyListViewAdapter  extends RecyclerView.Adapter<MyListViewAdapter.MyViewHolder>{

    private ArrayList<ShopBean> data;

    public MyListViewAdapter() {
        this.data = new ArrayList<>();
        ShopBean tempBean = new ShopBean();
        tempBean.setShopName("商品1");
        tempBean.setShopType("食品");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("食品");
        tempBean.setShopName("商品2");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("食品");
        tempBean.setShopName("商品3");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("食品");
        tempBean.setShopName("商品4");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("食品");
        tempBean.setShopName("商品5");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("食品");
        tempBean.setShopName("商品6");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品7");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品8");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品9");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品10");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品11");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("生活用品");
        tempBean.setShopName("商品12");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("文具");
        tempBean.setShopName("商品13");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("文具");
        tempBean.setShopName("商品14");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("文具");
        tempBean.setShopName("商品15");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("文具");
        tempBean.setShopName("商品16");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("文具");
        tempBean.setShopName("商品17");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品18");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品19");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品20");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品21");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品22");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品23");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("衣服");
        tempBean.setShopName("商品24");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品25");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品26");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品27");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品28");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品29");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品30");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品31");
        data.add(tempBean);
        tempBean = new ShopBean();
        tempBean.setShopType("游戏");
        tempBean.setShopName("商品32");
        data.add(tempBean);
    }

    public ArrayList<ShopBean> getData() {
        return data;
    }

    public void setData(ArrayList<ShopBean> data) {
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.activity_mylist_demo_item,null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 1;
        } else {
            if (data.get(position-1).getShopType().equals(data.get(position).getShopType())){
                return 0;
            } else {
                return 1;
            }
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (getItemViewType(position) == 1){//显示group title
            holder.tv_group_title.setText("标题 : "+data.get(position).getShopType());
            holder.tv_group_title.setVisibility(View.VISIBLE);
        } else {
            holder.tv_group_title.setVisibility(View.GONE);
        }
        holder.tv_item.setText("数据 : "+data.get(position).getShopName());
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_group_title,tv_item;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_group_title = (TextView) itemView.findViewById(R.id.tv_group_title);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }
    }

}
