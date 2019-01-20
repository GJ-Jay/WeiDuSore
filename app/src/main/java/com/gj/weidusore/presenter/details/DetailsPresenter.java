package com.gj.weidusore.presenter.details;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;
import com.gj.weidusore.presenter.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

public class DetailsPresenter extends BasePresenter {
    public DetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iLogin = NetWorkManager.mInstance().create(IRequest.class);
        return iLogin.details((long)args[0],(String) args[1],(int)args[2]);
    }
}
