package com.gj.weidusore.presenter;

import com.gj.weidusore.core.DataCall;
import com.gj.weidusore.core.http.IRequest;
import com.gj.weidusore.core.http.NetWorkManager;

import io.reactivex.Observable;

public class AddToCar extends BasePresenter {
    public AddToCar(DataCall baseCall) {
        super(baseCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iLogin = NetWorkManager.mInstance().create(IRequest.class);
        return iLogin.addTo((long)args[0],(String) args[1],(String) args[2]);
    }
}
