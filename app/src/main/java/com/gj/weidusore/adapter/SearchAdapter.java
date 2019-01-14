package com.gj.weidusore.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.Search;
import com.gj.weidusore.bean.homebean.GoodsList;
import com.gj.weidusore.core.WDApplication;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyHolder> {

    ArrayList<Search> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_search,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Search search = list.get(i);
        myHolder.search_title.setText(search.getCommodityName());
        myHolder.search_price.setText("￥"+search.getPrice());
        myHolder.search_num.setText("已售"+search.getSaleNum()+"件");
        myHolder.search_img.setImageURI(search.getMasterPic());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void delAll() {
        list.clear();//清空集合
    }

    public void addAll(List<Search> result) {
        if(result!=null){
            list.addAll(result);
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView search_img;
        private final TextView search_price;
        private final TextView search_title;
        private final TextView search_num;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            search_img = itemView.findViewById(R.id.search_img);
            search_price = itemView.findViewById(R.id.search_price);
            search_title = itemView.findViewById(R.id.search_title);
            search_num = itemView.findViewById(R.id.search_num);
        }
    }
}
