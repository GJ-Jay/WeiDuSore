package com.gj.weidusore.presenter;


import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * 注册
 */
public class RegPresenter extends BasePresenter {

    public RegPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.reg((String)args[0],(String)args[1]);
    }
}
