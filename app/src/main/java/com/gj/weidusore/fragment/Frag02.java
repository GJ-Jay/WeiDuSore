package com.gj.weidusore.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gj.weidusore.R;
import com.gj.weidusore.activity.mycircle.AddCircleActivity;
import com.gj.weidusore.adapter.CircleAdapter;
import com.gj.weidusore.bean.Circle;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.SpacingItemDecoration;
import com.gj.weidusore.core.WDFragment;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.CircleListPresenter;
import com.gj.weidusore.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Frag02 extends WDFragment implements XRecyclerView.LoadingListener {
    public static boolean addCircle;
    @BindView(R.id.xrlv)
    XRecyclerView xrlv;
    Unbinder unbinder;
    Unbinder unbinder1;
    private CircleListPresenter listPresenter;
    private CircleAdapter circleAdapter;

    @Override
    public String getPageName() {
        return "圈子fragment";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag02_two;
    }

    @Override
    protected void initView() {
        xrlv.setLayoutManager(new GridLayoutManager(getContext(), 1));//设置XRecyclerView

        int space = getResources().getDimensionPixelSize(R.dimen.dip_10); //给列表添加边距
        xrlv.addItemDecoration(new SpacingItemDecoration(space));//自定义的类

        circleAdapter = new CircleAdapter(getContext());

        xrlv.setAdapter(circleAdapter);
        xrlv.setLoadingListener(this);//加载监听
        listPresenter = new CircleListPresenter(new CircleCall()); //调用P层
        xrlv.refresh();
    }

    @Override
    public void onRefresh() {
        if (listPresenter.isRunning()) {//运行状态
            xrlv.refreshComplete();
            return;
        }
        listPresenter.reqeust(true, LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());//true为刷新数据
    }

    @Override
    public void onLoadMore() {
        if (listPresenter.isRunning()) {
            xrlv.loadMoreComplete();
            return;
        }
        listPresenter.reqeust(false, LOGIN_USER.getUserId(),
                LOGIN_USER.getSessionId());//false为不刷新数据
    }

    @OnClick(R.id.add_circle)
    public void addCircle(){
        Intent intent = new Intent(getContext(),AddCircleActivity.class);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    private class CircleCall implements DataCall<ResultLogin<List<Circle>>> {
        @Override
        public void success(ResultLogin<List<Circle>> data) {
            xrlv.loadMoreComplete();
            xrlv.refreshComplete();
            if (data.getStatus().equals("0000")) {
                if (listPresenter.getPage() == 1) {
                    circleAdapter.clear();//可忽略
                }
                circleAdapter.addAll(data.getResult());//添加列表并刷新
                circleAdapter.notifyDataSetChanged();
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
