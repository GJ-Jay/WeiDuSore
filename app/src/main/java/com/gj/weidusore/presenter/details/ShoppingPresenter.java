package com.gj.weidusore.presenter.details;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;
import com.gj.weidusore.presenter.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

public class ShoppingPresenter extends BasePresenter {
    public ShoppingPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iLogin = NetWorkManager.mInstance().create(IRequest.class);
        return iLogin.shopcar((long)args[0],(String) args[1]);
    }
}
