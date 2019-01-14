package com.gj.weidusore.presenter;


import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

public class LoginPresenter extends BasePresenter {

    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetWorkManager.mInstance().create(IRequest.class);
        return iRequest.login(args[0],args[1]);
    }
}
