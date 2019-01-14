package com.gj.weidusore.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.car.MyAddSubView;
import com.gj.weidusore.bean.car.Car;
import com.gj.weidusore.bean.car.CheckBoxs;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder>{
    private Context mContext;
    private List<Car> mList;
    private List<CheckBoxs> checkBoxs;

    public CarAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
        checkBoxs=new ArrayList<>();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.layout_car, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CarAdapter.ViewHolder holder, final int i) {
        Uri uri = Uri.parse(mList.get(i).getPic());
        holder.cartSdvPic.setImageURI(uri);
        holder.cartTxtCommodityName.setText(mList.get(i).getCommodityName());
        holder.cartTxtPrice.setText("￥"+mList.get(i).getPrice());
        holder.cartAddCount.setNumber(mList.get(i).getCount());
        /**
         * 点击商品时回调
         */
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCartlistener !=null){
                    boolean cbChecked = holder.cb.isChecked();
                    checkBoxs.get(i).setCk(cbChecked);
                    mCartlistener.GoodsChange();
                }
            }
        });

        if (checkBoxs.get(i).isCkeck()){
            holder.cb.setChecked(true);
        }else{
            holder.cb.setChecked(false);
        }

        holder.cartAddCount.setOnNumberChangeListener(new MyAddSubView.OnNumberChangeListener() {
            @Override
            public void onNumberChange(int num) {
                if (mCartlistener !=null){//点击加减号改变数字
                    mCartlistener.NumChange(i,num);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList.size()>0){
            for (int i = 0; i <mList.size(); i++) {
                checkBoxs.add(new CheckBoxs(false));
                if(checkBoxs.size()==mList.size()){
                    break;
                }
            }
        }
        return mList.size();
    }
    public void setData(List<Car> result) {
        if (result!=null){
            mList=result;
            notifyDataSetChanged();
        }
    }

    public void addAll(List<Car> result){
        if (result!=null){
            mList.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView cartSdvPic;
        private final TextView cartTxtCommodityName;
        private final TextView cartTxtPrice;
        private final MyAddSubView cartAddCount;
        private final CheckBox cb;
        public ViewHolder(View itemView) {
            super(itemView);
            cb = itemView.findViewById(R.id.cb);
            cartSdvPic = itemView.findViewById(R.id.cart_sdv_pic);
            cartTxtCommodityName = itemView.findViewById(R.id.cart_txt_commodityName);
            cartTxtPrice = itemView.findViewById(R.id.cart_txt_price);
            cartAddCount = itemView.findViewById(R.id.cart_add_count);
        }
    }

    /**
     * 定义接口回调
     */
    public interface Cartlistener{
        void GoodsChange();
        void NumChange(int index,int num);
    }
    private Cartlistener mCartlistener;

    public void setCartlistener(Cartlistener cartlistener){
        mCartlistener = cartlistener;
    }

    /**
     * 计算商品总价
     * @return
     */
    public float setsumprice(){
        float num = 0.0f;
        for (int i = 0; i <mList.size() ; i++) {
            if(checkBoxs.get(i).isCkeck()==true){
                int count = mList.get(i).getCount();
                int price = mList.get(i).getPrice();
                num+=count*price;
            }
        }
        return num;
    }

    /**
     * 改变商品数量
     * @param index
     * @param num
     */

    public void changeshopnum(int index,int num){
        mList.get(index).setCount(num);
    }
    public void setAllshopselected(boolean b){
        for (int i = 0; i <checkBoxs.size() ; i++) {
            checkBoxs.get(i).setCk(b?true:false);
        }
    }

    /**
     * 所有商品是否都选中
     * @return
     */
    public boolean isAllshopselected(){
        for (int i = 0; i < mList.size(); i++) {
            if (checkBoxs.get(i).isCkeck() ==false){
                return false;
            }
        }
        return true;
    }

    /**
     * 是否所有商品被选中
     * @return
     */
    public boolean isAllshopselecteds(){
        mCartlistener.GoodsChange();
        for (int i = 0; i < mList.size(); i++) {
            if (checkBoxs.get(i).isCkeck() == false){
                return false;
            }
        }
        return true;
    }


    /**
     * 商品总数
     * @return
     */
    public int setshopNum(){
        int shopnum = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (checkBoxs.get(i).isCkeck() ==true){
                shopnum += mList.get(i).getCount();
            }
        }
        return shopnum;
    }

}
