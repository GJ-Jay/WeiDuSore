package com.gj.weidusore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gj.weidusore.R;
import com.gj.weidusore.adapter.order.Over_Adapter;
import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.bean.order.Null_Bean;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.WDFragment;
import com.gj.weidusore.core.exception.ApiException;
import com.gj.weidusore.presenter.order.Null_Pay_Presenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment05 extends WDFragment {
    @BindView(R.id.recy)
    RecyclerView recy;
    private Null_Pay_Presenter null_pay_presenter;
    private Over_Adapter over_adapter;

    @Override
    public String getPageName() {
        return "已完成";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment05;
    }

    @Override
    protected void initView() {
        String sessionId = LOGIN_USER.getSessionId();
        long userId = LOGIN_USER.getUserId();

        null_pay_presenter = new Null_Pay_Presenter(new CallBack());
        null_pay_presenter.reqeust(userId, sessionId, 1, 5, 9); //最后一个参数改0=查看全部 1=查看待付款 2=查看待收货 3=查看待评价 9=查看已完成
        over_adapter = new Over_Adapter(getActivity());
        recy.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recy.setAdapter(over_adapter);
    }

    private class CallBack implements DataCall<ResultLogin<List<Null_Bean>>> {
        @Override
        public void success(ResultLogin<List<Null_Bean>> data) {
            over_adapter.setData(data.getOrderList());
            Log.e("aaa", data.getMessage() + "===");
            Log.e("aaa", data.getOrderList().size() + "===");
        }

        @Override
        public void fail(ApiException e) {

        }
    }
}
