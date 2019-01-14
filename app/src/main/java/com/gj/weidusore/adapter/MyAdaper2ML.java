package com.gj.weidusore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.Goods2ProductActivity;
import com.gj.weidusore.activity.GoodsProductActivity;
import com.gj.weidusore.bean.homebean.GoodsList;
import com.gj.weidusore.core.WDApplication;

import java.util.ArrayList;
import java.util.List;

public class MyAdaper2ML extends RecyclerView.Adapter<MyAdaper2ML.MyHolder>{

    List<GoodsList.MlssBean.CommodityListBeanXX> list = new ArrayList<>();
    Context context;
    private GoodsList.MlssBean.CommodityListBeanXX commodityListBeanXX;

    public MyAdaper2ML(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_mlss_fashion2,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        commodityListBeanXX = list.get(i);
        myHolder.ml_title.setText(commodityListBeanXX.getCommodityName());
        myHolder.ml_price.setText("￥"+ commodityListBeanXX.getPrice());
        Glide.with(WDApplication.getContext()).load(commodityListBeanXX.getMasterPic())
                .into(myHolder.ml_img);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context,GoodsProductActivity.class);
                Intent intent = new Intent(context,GoodsProductActivity.class);
                intent.putExtra("commodityId", commodityListBeanXX.getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<GoodsList.MlssBean.CommodityListBeanXX> commodityList1) {
        if(commodityList1!=null){
            list.addAll(commodityList1);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView ml_img;
        private final TextView ml_title;
        private final TextView ml_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ml_img = itemView.findViewById(R.id.ml_img);
            ml_title = itemView.findViewById(R.id.ml_title);
            ml_price = itemView.findViewById(R.id.ml_price);
        }
    }
}
