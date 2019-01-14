package com.gj.weidusore.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gj.weidusore.R;
import com.gj.weidusore.bean.Circle;
import com.gj.weidusore.bean.MyCircle;
import com.gj.weidusore.core.SpacingItemDecoration;
import com.gj.weidusore.util.DateUtils;
import com.gj.weidusore.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MyCircleAdapter extends RecyclerView.Adapter<MyCircleAdapter.MyHolder> {

    Context context;
    public MyCircleAdapter(Context context) {
        this.context = context;
    }

    ArrayList<MyCircle> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_mycircle,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        MyCircle myCircle = list.get(i);
        myHolder.title.setText(myCircle.getContent());
        try {
            myHolder.time.setText(DateUtils.dateFormat(new Date(myCircle.getCreateTime()),
                    DateUtils.MINUTE_PATTERN));//设置时间
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(StringUtils.isEmpty(myCircle.getImage())){//图片为空
            myHolder.recyclerView.setVisibility(View.GONE);//没有图片设为空
        }else {
            myHolder.recyclerView.setVisibility(View.VISIBLE);//没有图片设为空
            String[] images = myCircle.getImage().split(",");
            int colNum;//列数
            if(images.length==1){
                colNum = 1;
            }else if(images.length==2||images.length == 4){
                colNum = 2;
            }else{
                colNum = 3;
            }
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数

            myHolder.myCircleImageAdapter.clear();//清空图片适配器集合
            for (int j = 0; j <colNum ; j++) {
                myHolder.myCircleImageAdapter.addAll(Arrays.asList(images));
            }//添加图片的聚合
            myHolder.myCircleImageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(List<MyCircle> result) {
        if(result!=null){
            list.addAll(result);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final RecyclerView recyclerView;
        private final TextView time;
        private final MyCircleImageAdapter myCircleImageAdapter;
        private final GridLayoutManager gridLayoutManager;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.circle_title);
            recyclerView = itemView.findViewById(R.id.circle_grid_view);
            time = itemView.findViewById(R.id.circle_time);
            myCircleImageAdapter = new MyCircleImageAdapter();

            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);//图片间距
            gridLayoutManager = new GridLayoutManager(context,3);//设置布局管理器
            recyclerView.addItemDecoration(new SpacingItemDecoration(space));//添加视图的间距
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(myCircleImageAdapter);
        }
    }
}
