package com.gj.weidusore.presenter.order;


import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;
import com.gj.weidusore.presenter.BasePresenter;

import io.reactivex.Observable;

public class Null_Pay_Presenter extends BasePresenter {
    public Null_Pay_Presenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iLogin = NetWorkManager.mInstance().create(IRequest.class);
        return iLogin.order((long) args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
