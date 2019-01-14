package com.gj.weidusore.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.bean.Circle;
import com.gj.weidusore.core.SpacingItemDecoration;
import com.gj.weidusore.util.DateUtils;
import com.gj.weidusore.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.MyHolder> {

    Context context;
    public CircleAdapter(Context context) {
        this.context = context;
    }

    ArrayList<Circle> list = new ArrayList<>();

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(),R.layout.layout_circle,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
        Circle circle = list.get(i);
        myHolder.img_header.setImageURI(Uri.parse(circle.getHeadPic()));//注意设置头像Uri.parse(circle.getHeadPic())
        myHolder.name.setText(circle.getNickName());
        try {
            myHolder.time.setText(DateUtils.dateFormat(new Date(circle.getCreateTime()),
                    DateUtils.MINUTE_PATTERN));//设置时间
        } catch (ParseException e) {
            e.printStackTrace();
        }
        myHolder.title.setText(circle.getContent());

        if(StringUtils.isEmpty(circle.getImage())){//图片为空
            myHolder.gridview.setVisibility(View.GONE);//没有图片设为空
        }else {
            myHolder.gridview.setVisibility(View.VISIBLE);//没有图片设为空
            String[] images = circle.getImage().split(",");
           /* int imageCount = (int) (Math.random()*9)+1;*/
            int colNum;//列数
            if(images.length==1){
//            if(imageCount==1){
                colNum = 1;
//                imageCount = 1;
            }else if(images.length==2||images.length == 4){
//            }else if(imageCount==2||imageCount == 4){
                colNum = 2;
//                imageCount = 2;
            }else{
                colNum = 3;
//                imageCount = 3;
            }
            myHolder.gridLayoutManager.setSpanCount(colNum);//设置列数

            myHolder.imageAdapter.clear();//清空图片适配器集合
            for (int j = 0; j <colNum ; j++) {
                myHolder.imageAdapter.addAll(Arrays.asList(images));
            }//添加图片的聚合
            myHolder.imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clear() {
        list.clear();
    }

    public void addAll(List<Circle> result) {
        if(result!=null){
            list.addAll(result);
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView img_header;
        private final TextView name;
        private final TextView title;
        private final TextView time;
        private final RecyclerView gridview;
        private final ImageAdapter imageAdapter;
        private final GridLayoutManager gridLayoutManager;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            img_header = itemView.findViewById(R.id.circle_img_header);
            name = itemView.findViewById(R.id.circle_name);
            time = itemView.findViewById(R.id.circle_time);
            title = itemView.findViewById(R.id.circle_title);
            gridview = itemView.findViewById(R.id.circle_grid_view);//底部朋友圈的图片
            imageAdapter = new ImageAdapter();
//            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);//图片间距
//            //设置布局管理器
//            gridLayoutManager = new GridLayoutManager(context,
//                    3);
            int space = context.getResources().getDimensionPixelSize(R.dimen.dip_10);;//图片间距
            gridLayoutManager = new GridLayoutManager(context,3);
            gridview.addItemDecoration(new SpacingItemDecoration(space));//添加视图的间距
            gridview.setLayoutManager(gridLayoutManager);
            gridview.setAdapter(imageAdapter);
        }
    }
}
