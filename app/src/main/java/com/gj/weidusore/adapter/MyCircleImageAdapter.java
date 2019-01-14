package com.gj.weidusore.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;

import java.util.ArrayList;
import java.util.List;

class MyCircleImageAdapter extends RecyclerView.Adapter<MyCircleImageAdapter.MyHolder> {

    ArrayList<String> mList = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.myciecle_img_item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        myHolder.img.setImageURI(mList.get(i));//设置图片
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void clear() {
        mList.clear();
    }

    public void addAll(List<String> strings) {
        if(strings!=null){
            mList.addAll(strings);
        }
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.mycircle_img_item);
        }
    }
}
