package com.gj.weidusore.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.gj.weidusore.R;
import com.gj.weidusore.activity.SearchActivity;
import com.gj.weidusore.adapter.MyAdaper1RX;
import com.gj.weidusore.adapter.MyAdaper2ML;
import com.gj.weidusore.adapter.MyAdaper3PZ;
import com.gj.weidusore.bean.GoodsBanner;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.homebean.GoodsList;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.BannerPresenter;
import com.gj.weidusore.presenter.GoodsListPresenter;
import com.gj.weidusore.util.UIUtils;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Frag01 extends Fragment implements View.OnClickListener {
    @BindView(R.id.top)
    RelativeLayout top;
    Unbinder unbinder;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rlv1_rx)
    RecyclerView rlv1Rx;
    @BindView(R.id.rlv2_ml)
    RecyclerView rlv2Ml;
    @BindView(R.id.rlv3_pz)
    RecyclerView rlv3Pz;
    @BindView(R.id.text_search)
    TextView textSearch;
    private MyAdaper1RX myAdaper1RX;
    private MyAdaper2ML myAdaper2ML;
    private MyAdaper3PZ myAdaper3PZ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag01_first, container, false);
        unbinder = ButterKnife.bind(this, view);

        textSearch.setOnClickListener(this);

        BannerPresenter bannerPresenter = new BannerPresenter(new MyBanner());//调用P层
        GoodsListPresenter listPresenter = new GoodsListPresenter(new MyList());

        myAdaper1RX = new MyAdaper1RX(getActivity());
        myAdaper2ML = new MyAdaper2ML(getActivity());
        myAdaper3PZ = new MyAdaper3PZ(getActivity());

        StaggeredGridLayoutManager rx = new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager ml = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        StaggeredGridLayoutManager pz = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);

        rlv1Rx.setLayoutManager(rx);//添加布局管理器
        rlv1Rx.setAdapter(myAdaper1RX);//添加适配器
        rlv2Ml.setLayoutManager(ml);
        rlv2Ml.setAdapter(myAdaper2ML);
        rlv3Pz.setLayoutManager(pz);
        rlv3Pz.setAdapter(myAdaper3PZ);
        bannerPresenter.reqeust();
        listPresenter.reqeust();
        return view;
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(),SearchActivity.class));
    }

    class MyBanner implements DataCall<ResultLogin<List<GoodsBanner>>> {

        @Override
        public void success(ResultLogin<List<GoodsBanner>> data) {
            if (data.getStatus().equals("0000")) {//判断请求成功
                ArrayList<String> list = new ArrayList<>();//创建对象
                List<GoodsBanner> result = data.getResult();
                for (int i = 0; i < result.size(); i++) {
                    list.add(result.get(i).getImageUrl());
                }
                banner.setImages(list);
                banner.setImageLoader(new MyImage());
                banner.start();
            }
        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class MyList implements DataCall<ResultLogin<GoodsList>> {
        @Override
        public void success(ResultLogin<GoodsList> data) {
            UIUtils.showToastSafe(data.getMessage());
            if (data.getStatus().equals("0000")) {//判断成功
                List<GoodsList.RxxpBean> rxxp = data.getResult().getRxxp();
                List<GoodsList.MlssBean> mlss = data.getResult().getMlss();
                List<GoodsList.PzshBean> pzsh = data.getResult().getPzsh();

                List<GoodsList.RxxpBean.CommodityListBean> commodityList = rxxp.get(0).getCommodityList();//获取数据
                List<GoodsList.MlssBean.CommodityListBeanXX> commodityList1 = mlss.get(0).getCommodityList();
                List<GoodsList.PzshBean.CommodityListBeanX> commodityList2 = pzsh.get(0).getCommodityList();


                myAdaper1RX.addList(commodityList);//添加集合
                myAdaper1RX.notifyDataSetChanged();
                myAdaper2ML.addList(commodityList1);
                myAdaper2ML.notifyDataSetChanged();
                myAdaper3PZ.addList(commodityList2);
                myAdaper3PZ.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            UIUtils.showToastSafe(e.getDisplayMessage() + "" + e.getCode());
        }
    }

    class MyImage extends ImageLoader {//banner的具体操作

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Uri parse = Uri.parse((String) path);//使用fresco
            imageView.setImageURI(parse);
        }

        @Override
        public ImageView createImageView(Context context) {
            SimpleDraweeView simpleDraweeView =
                    (SimpleDraweeView) View.inflate(getContext(), R.layout.layout_banner, null);
            return simpleDraweeView;
        }
    }
}
