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
import com.gj.weidusore.activity.DetailsActivity;
import com.gj.weidusore.activity.Goods2ProductActivity;
import com.gj.weidusore.activity.GoodsProductActivity;
import com.gj.weidusore.bean.homebean.GoodsList;
import com.gj.weidusore.core.WDApplication;

import java.util.ArrayList;
import java.util.List;

public class MyAdaper1RX extends RecyclerView.Adapter<MyAdaper1RX.MyHolder> {

    List<GoodsList.RxxpBean.CommodityListBean> list = new ArrayList<>();
    Context context;
    private GoodsList.RxxpBean.CommodityListBean commodityListBean;

    public MyAdaper1RX(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_rxxp_hot1,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        commodityListBean = list.get(i);
        myHolder.rx_title.setText(commodityListBean.getCommodityName());
        myHolder.rx_price.setText("￥"+ commodityListBean.getPrice());
        Glide.with(WDApplication.getContext()).load(commodityListBean.getMasterPic())
                .into(myHolder.rx_img);
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context,GoodsProductActivity.class);
                Intent intent = new Intent(context,DetailsActivity.class);
                intent.putExtra("commodityId", commodityListBean.getCommodityId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<GoodsList.RxxpBean.CommodityListBean> commodityList) {
        if(commodityList!=null){
            list.addAll(commodityList);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final ImageView rx_img;
        private final TextView rx_title;
        private final TextView rx_price;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            rx_img = itemView.findViewById(R.id.rx_img);
            rx_title = itemView.findViewById(R.id.rx_title);
            rx_price = itemView.findViewById(R.id.rx_price);
        }
    }
}
