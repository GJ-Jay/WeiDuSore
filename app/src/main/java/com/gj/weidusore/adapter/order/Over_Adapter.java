package com.gj.weidusore.adapter.order;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.bean.order.Null_Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 布局未改
 */
public class Over_Adapter extends RecyclerView.Adapter<Over_Adapter.ViewHolder> {
    private final FragmentActivity context;
    private List<Null_Bean> list;
    private Null_Pay_Item_Adapter null_pay_item_adapter;
    public Over_Adapter(FragmentActivity activity) {
        this.context=activity;
        this.list=new ArrayList<>();
        this.null_pay_item_adapter=new Null_Pay_Item_Adapter(activity);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.over_item, null);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.e("a1",list.get(position).getExpressCompName()+"===");
        List<Null_Bean.DetailListBean> detailList = list.get(position).getDetailList();
        int p=0;
        for (int i=0;i<detailList.size();i++){
            p += detailList.get(i).getCommodityPrice();

        }
        holder.price.setText(p+"");
        holder.num.setText(detailList.size()+"");
        holder.ordernum.setText(list.get(position).getExpressSn()+"");
        //设置recycler横向
        holder.recy.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        //商品详情的adapter 放入集合里的数据
        null_pay_item_adapter.setData(list.get(position).getDetailList());
        //把商品详情的adapter在设置进recycler里展示
        holder.recy.setAdapter(null_pay_item_adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<Null_Bean> orderList) {
        if (this.list!=null){
            this.list.clear();
            this.list=orderList;
        Log.e("a1",list.size()+"===");
           notifyDataSetChanged();
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button pay,cancel;
        TextView price,num,data,ordernum;
        RecyclerView recy;
        public ViewHolder(View itemView) {
            super(itemView);
            pay=itemView.findViewById(R.id.pay);
            cancel=itemView.findViewById(R.id.cancel);
            price=itemView.findViewById(R.id.price);
            num=itemView.findViewById(R.id.num);
            data=itemView.findViewById(R.id.data);
            ordernum=itemView.findViewById(R.id.ordernum);
            recy=itemView.findViewById(R.id.recy);
        }
    }
}
