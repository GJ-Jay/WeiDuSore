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

public class MyAdaper3PZ extends RecyclerView.Adapter<MyAdaper3PZ.MyHolder> {

    List<GoodsList.PzshBean.CommodityListBeanX> list = new ArrayList<>();
    Context context;
    private GoodsList.PzshBean.CommodityListBeanX commodityListBeanX;

    public MyAdaper3PZ(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_pzss_live3,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        commodityListBeanX = list.get(i);
        myHolder.pz_title.setText(commodityListBeanX.getCommodityName());
        myHolder.pz_price.setText("￥"+ commodityListBeanX.getPrice());
        Glide.with(WDApplication.getContext()).load(commodityListBeanX.getMasterPic())
                .into(myHolder.pz_img);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context,GoodsProductActivity.class);
                Intent intent = new Intent(context,GoodsProductActivity.class);
                intent.putExtra("commodityId", commodityListBeanX.getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<GoodsList.PzshBean.CommodityListBeanX> commodityList2) {
        if(commodityList2!=null){
            list.addAll(commodityList2);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView pz_img;
        private final TextView pz_title;
        private final TextView pz_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            pz_img = itemView.findViewById(R.id.pz_img);
            pz_title = itemView.findViewById(R.id.pz_title);
            pz_price = itemView.findViewById(R.id.pz_price);
        }
    }
}
