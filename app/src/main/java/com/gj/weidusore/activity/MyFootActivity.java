package com.gj.weidusore.activity;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.MyFootAdapter;
import com.gj.weidusore.bean.Foot;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDActivity;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.FootPresenter;
import com.gj.weidusore.util.UIUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyFootActivity extends WDActivity implements XRecyclerView.LoadingListener {

    @BindView(R.id.xrlv)
    XRecyclerView xrlv;
    private MyFootAdapter myFootAdapter;
    private FootPresenter footPresenter;

    @Override
    protected void initView() {
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(
                2,  StaggeredGridLayoutManager.VERTICAL);//设置布局管理器 瀑布流
        xrlv.setLayoutManager(layoutManager);

        myFootAdapter = new MyFootAdapter(this);
        xrlv.setAdapter(myFootAdapter);
        xrlv.setLoadingListener(this);

        //调用P层
        footPresenter = new FootPresenter(new FootCall());
        xrlv.refresh();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_foot;
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
        if(footPresenter.isRunning()){
            xrlv.refreshComplete();
            return;
        }
        footPresenter.reqeust(true,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
    }

    @Override
    public void onLoadMore() {
        if(footPresenter.isRunning()){
            xrlv.loadMoreComplete();
            return;
        }
        footPresenter.reqeust(false,LOGIN_USER.getUserId(),LOGIN_USER.getSessionId());
    }

    private class FootCall implements DataCall<ResultLogin<List<Foot>>> {
        @Override
        public void success(ResultLogin<List<Foot>> data) {
            xrlv.refreshComplete();
            xrlv.loadMoreComplete();
            if(data.getStatus().equals("0000")){
                List<Foot> result = data.getResult();
                myFootAdapter.addAll(result);
                myFootAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException e) {
            xrlv.loadMoreComplete();
            xrlv.refreshComplete();
            UIUtils.showToastSafe("获取足迹失败了"+e.getMessage());
        }
    }
}
