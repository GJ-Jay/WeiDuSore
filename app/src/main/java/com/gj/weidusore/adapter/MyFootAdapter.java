package com.gj.weidusore.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.MyFootActivity;
import com.gj.weidusore.bean.Foot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyFootAdapter extends RecyclerView.Adapter<MyFootAdapter.MyHolder> {
   Context context;
    public MyFootAdapter(Context context) {
        this.context = context;
    }

    ArrayList<Foot> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.layout_myfoot, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Foot foot = list.get(i);
        myHolder.img.setImageURI(foot.getMasterPic());
        myHolder.title.setText(foot.getCommodityName());
        myHolder.price.setText("￥"+foot.getPrice());
        myHolder.see.setText("已浏览"+foot.getBrowseNum()+"次");

        Date date = new Date(foot.getBrowseTime());//设置时间
        SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = formatdate.format(date);
        myHolder.time.setText(format);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<Foot> result) {
        if(result!=null){
            list.addAll(result);
        }
    }


    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img;
        private final TextView title;
        private final TextView price;
        private final TextView see;
        private final TextView time;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.myfoot_img);
            title = itemView.findViewById(R.id.mtfoot_title);
            price = itemView.findViewById(R.id.mtfoot_price);
            see = itemView.findViewById(R.id.myfoot_see);
            time = itemView.findViewById(R.id.myfoot_time);
        }
    }
}
