package com.gj.weidusore.presenter;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;

/*
 详情  首页列表3个
 */
public class GoodsPresenter extends BasePresenter{
    public GoodsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.homegoods((long)args[0],(String)args[1], (int)args[2]);
    }
}
