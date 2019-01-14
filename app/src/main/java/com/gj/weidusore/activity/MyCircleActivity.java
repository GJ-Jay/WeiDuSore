package com.gj.weidusore.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.ImageView;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.MyCircleAdapter;
import com.gj.weidusore.bean.MyCircle;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.SpacingItemDecoration;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.MyCirclePresenter;
import com.gj.weidusore.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyCircleActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.mycircle_delete)
    ImageView mycircleDelete;
    @BindView(R.id.xrlv)
    XRecyclerView xrlv;
    private MyCircleAdapter myCircleAdapter;
    private MyCirclePresenter myCirclePresenter;

    @Override
    protected void initView() {
        xrlv.setLayoutManager(new GridLayoutManager(this,1));

        int space = getResources().getDimensionPixelSize(R.dimen.dip_20); //给列表添加边距
        xrlv.addItemDecoration(new SpacingItemDecoration(space));//自定义的类

        myCircleAdapter = new MyCircleAdapter(this);
        xrlv.setAdapter(myCircleAdapter);
        xrlv.setLoadingListener(this);//加载监听
        //调用P层
        myCirclePresenter = new MyCirclePresenter(new MyCircleCall());
        xrlv.refresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_circle;
    }

    @Override
    protected void destoryData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onRefresh() {
        if(myCirclePresenter.isRunning()){//运行状态
            xrlv.refreshComplete();
            return;
        }
        myCirclePresenter.reqeust(true,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());//true为刷新数据
    }

    @Override
    public void onLoadMore() {
        if(myCirclePresenter.isRunning()){
            xrlv.loadMoreComplete();
            return;
        }
        myCirclePresenter.reqeust(false,LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());//false为不刷新数据
    }

    private class MyCircleCall implements DataCall<ResultLogin<List<MyCircle>>> {
        @Override
        public void success(ResultLogin<List<MyCircle>> data) {
            xrlv.loadMoreComplete();
            xrlv.refreshComplete();
            if(data.getStatus().equals("0000")){
                if(myCirclePresenter.getPage()==1){
                    myCircleAdapter.clear();//可忽略
                }
                myCircleAdapter.addAll(data.getResult());//添加列表并刷新
                myCircleAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            xrlv.refreshComplete();
            xrlv.loadMoreComplete();
            UIUtils.showToastSafe(e.getMessage());
        }
    }
}
