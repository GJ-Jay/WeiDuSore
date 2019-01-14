package com.gj.weidusore.presenter;

import com.gj.weidusore.bean.ResultLogin;
import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;

public class GoodsListPresenter extends BasePresenter{

    public GoodsListPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.getList();
    }
}
