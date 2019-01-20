package com.gj.weidusore.adapter.car;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.car.AddSubLayout;
import com.gj.weidusore.bean.car.ShopCar;

import java.util.ArrayList;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.MyHolder> {
    private List<ShopCar> list = new ArrayList<>();
    public void addList(List<ShopCar> u){
        if(u!=null){
            list.addAll(u);
        }
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_tijiao, viewGroup, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        final ShopCar shoppingBean = list.get(i);
        //设置内容
        myHolder.text_name.setText(shoppingBean.getCommodityName());
        //设置单价
        myHolder.text_price.setText("￥" + shoppingBean.getPrice());
        //Glide.with(MyApp.getContext()).load(shoppingBean.getPic()).into(myHolder.imageView);
        myHolder.addSubLayout.setCount(shoppingBean.getCount());
        myHolder.addSubLayout.setAddSubListener(new AddSubLayout.AddSubListener() {
            @Override
            public void addSub(int count) {
                shoppingBean.setCount(count);
                countListener.getCount(count);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView text_name;
        private final TextView text_price;
        private final AddSubLayout addSubLayout;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ti_image);
            text_name = itemView.findViewById(R.id.tijiao_name);
            text_price = itemView.findViewById(R.id.tijiao_price);
            addSubLayout = itemView.findViewById(R.id.tijiao_add);
        }
    }
    private CountListener countListener;
    public interface CountListener{
        void getCount(int count);
    }
    public void setCountListener(CountListener countListener){
        this.countListener = countListener;
    }
}
